# HomeCam Cloudflare Worker

This Worker accepts only WebRTC signaling metadata: SDP offers, SDP answers, ICE candidates, viewer commands, and device status. It never accepts, proxies, or relays audio/video media.

## Deploy

```bash
npm install
npm run deploy
```

After deployment, copy the HTTPS Worker URL into HomeCam settings on both devices.

## Storage model

The Worker uses the Cloudflare Workers Cache API with short-lived entries for free-tier signaling. Entries expire quickly and contain only signaling envelopes, commands, and timestamps. WebRTC media stays encrypted and peer-to-peer between Android devices.

## Endpoints

- `POST /offer`
- `POST /answer`
- `POST /candidate`
- `POST /command`
- `GET /status`
