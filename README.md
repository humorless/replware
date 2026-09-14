The site is plain static HTML in `resources/public/` (`index.html`, `en/`, `assets/`, `CNAME`).
There is no build step: edit the HTML directly.

# How to preview

```
bb serve
# http://localhost:8000/     中文
# http://localhost:8000/en/  English
```

# How to deploy

Push `boot-code`. `.github/workflows/deploy.yml` publishes `resources/public` to `master`,
which GitHub Pages serves at https://replware.dev.
