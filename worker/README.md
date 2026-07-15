# HomeCam Cloudflare Worker

This Worker stores only signaling envelopes: offers, answers, ICE candidates, commands, and status. Media is never accepted or relayed.

## Deploy
1. Create a Cloudflare KV namespace.
2. Put the production and preview IDs in `wrangler.toml`.
3. Run `npm install` and `npm run deploy`.
4. Paste the deployed HTTPS URL into HomeCam settings.
