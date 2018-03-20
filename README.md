# AxonFramework - CQRS - Event Sourcing

```bash
mvn clean install -P build-docker-images
docker-compose up -d
curl -d '{"pageId": "c0d881e3-3f14-45e6-81fc-284c23016a6f", "layoutId": "1abd9549-d943-4519-8202-92c899a80864", "slug": "article-1", "name":"Article 1"}' -H 'Content-Type: application/json' localhost:17000/command-service/pages 
curl -d '{"contentId": "1abd9549-d943-4519-8202-92c899a80864", "contentType": "article", "order":1}' -H 'Content-Type: application/json' localhost:17000/command-service/pages/c0d881e3-3f14-45e6-81fc-284c23016a6f/elements
curl localhost:17000/query-service/pages 
```
