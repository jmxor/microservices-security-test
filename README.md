# Project setup

The aim of project is to provide an environment for two spring servers to
communicate that is as close a spossible to a production deployment while still
being entirely local. This includes full SSL/HTTPS, CORS and a reverse proxy.

## Generate local certificates

TODO

## Simulate Different domains for CORS Testing

Add the following to the `/etc/hosts`

```bash
127.0.0.1 api.local.test
127.0.0.1 app.local.test
127.0.0.1 keycloak.local.test
```

TODO add keycloak testcontainer
