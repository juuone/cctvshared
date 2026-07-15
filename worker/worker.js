const DEVICE = /^HOME[0-9A-Z]{5,24}$/;
const TOKEN = /^[a-fA-F0-9]{64}$/;
const COMMANDS = new Set(['START', 'STOP', 'FLASH_ON', 'FLASH_OFF', 'FRONT_CAMERA', 'BACK_CAMERA', 'ENABLE_AUDIO', 'DISABLE_AUDIO', 'HD_ON', 'HD_OFF', 'PING']);
const TTL_SECONDS = 120;

export default {
  async fetch(request) {
    try {
      const url = new URL(request.url);
      if (request.method === 'OPTIONS') {
        return cors(new Response(null));
      }
      if (url.pathname === '/status' && request.method === 'GET') {
        return status(url);
      }
      if (request.method !== 'POST') {
        return json({ error: 'method_not_allowed' }, 405);
      }
      const body = await request.json();
      validateIdentity(body);
      if (url.pathname === '/offer') {
        return storeEnvelope(url, body, 'offer');
      }
      if (url.pathname === '/answer') {
        return storeEnvelope(url, body, 'answer');
      }
      if (url.pathname === '/candidate') {
        return storeCandidate(url, body);
      }
      if (url.pathname === '/command') {
        return storeCommand(url, body);
      }
      return json({ error: 'not_found' }, 404);
    } catch (error) {
      return json({ error: error.message || 'bad_request' }, 400);
    }
  }
};

async function storeEnvelope(url, body, type) {
  if (body.type !== 'offer' && body.type !== 'answer') {
    throw new Error('invalid_sdp_type');
  }
  if (typeof body.sdp !== 'string' || body.sdp.length > 200000) {
    throw new Error('invalid_sdp');
  }
  await cachePut(url, key(url, body.deviceId, type), { type: body.type, sdp: body.sdp, createdAt: Date.now() });
  await touch(url, body.deviceId);
  return json({ ok: true });
}

async function storeCandidate(url, body) {
  if (typeof body.candidate !== 'string' || body.candidate.length > 4096 || typeof body.sdpMid !== 'string' || !Number.isInteger(body.sdpMLineIndex)) {
    throw new Error('invalid_candidate');
  }
  const candidate = { sdpMid: body.sdpMid, sdpMLineIndex: body.sdpMLineIndex, candidate: body.candidate, createdAt: Date.now() };
  const existing = await cacheGet(url, key(url, body.deviceId, 'candidates')) || [];
  existing.push(candidate);
  await cachePut(url, key(url, body.deviceId, 'candidates'), existing.slice(-50));
  await touch(url, body.deviceId);
  return json({ ok: true });
}

async function storeCommand(url, body) {
  if (!COMMANDS.has(body.command)) {
    throw new Error('invalid_command');
  }
  const nonce = String(body.nonce || '');
  if (nonce.length < 8 || nonce.length > 128) {
    throw new Error('invalid_nonce');
  }
  const nonceKey = key(url, body.deviceId, `nonce/${nonce}`);
  if (await cacheGet(url, nonceKey)) {
    throw new Error('replay_detected');
  }
  await cachePut(url, nonceKey, { used: true });
  await cachePut(url, key(url, body.deviceId, 'command'), { command: body.command, createdAt: Date.now() });
  await touch(url, body.deviceId);
  return json({ ok: true });
}

async function status(url) {
  const deviceId = url.searchParams.get('deviceId') || '';
  const token = url.searchParams.get('token') || '';
  validateIdentity({ deviceId, token });
  const [offer, answer, candidates, command, lastSeen] = await Promise.all([
    cacheGet(url, key(url, deviceId, 'offer')),
    cacheGet(url, key(url, deviceId, 'answer')),
    cacheGet(url, key(url, deviceId, 'candidates')),
    cacheGet(url, key(url, deviceId, 'command')),
    cacheGet(url, key(url, deviceId, 'lastSeen'))
  ]);
  return json({ deviceId, online: Boolean(lastSeen), offer, answer, candidates: candidates || [], command, lastSeen: lastSeen ? lastSeen.value : 0 });
}

function validateIdentity(body) {
  if (!body || !DEVICE.test(body.deviceId || '') || !TOKEN.test(body.token || '')) {
    throw new Error('invalid_identity');
  }
}

async function touch(url, deviceId) {
  await cachePut(url, key(url, deviceId, 'lastSeen'), { value: Date.now() });
}

function key(url, deviceId, name) {
  return new Request(`${url.origin}/cache/${encodeURIComponent(deviceId)}/${name}`);
}

async function cachePut(url, request, value) {
  const response = new Response(JSON.stringify(value), {
    headers: {
      'content-type': 'application/json',
      'cache-control': `public, max-age=${TTL_SECONDS}`
    }
  });
  await caches.default.put(request, response);
}

async function cacheGet(url, request) {
  const response = await caches.default.match(request);
  return response ? response.json() : null;
}

function json(value, status = 200) {
  return cors(new Response(JSON.stringify(value), { status, headers: { 'content-type': 'application/json' } }));
}

function cors(response) {
  response.headers.set('access-control-allow-origin', '*');
  response.headers.set('access-control-allow-methods', 'GET,POST,OPTIONS');
  response.headers.set('access-control-allow-headers', 'content-type');
  return response;
}
