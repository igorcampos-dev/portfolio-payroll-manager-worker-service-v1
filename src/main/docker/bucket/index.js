const http = require('http');
const { createProxyMiddleware } = require('http-proxy-middleware');

const S3rver = require('s3rver');
const awsDirectory = './aws';
const s3rverPort = 4569;
const s3rverHostname = '0.0.0.0';
const s3rverSilent = true;
const s3rverDirectory = awsDirectory;

const s3rverParams = {
  port: s3rverPort,
  silent: s3rverSilent,
  hostname: s3rverHostname,
  directory: s3rverDirectory,
};

const s3rver = new S3rver(s3rverParams);

s3rver.run((err, host, port) => {
  if (err) {
    console.error(err);
  } else {
    console.log('S3rver listening on host %s', host);
  }
});

const proxyOptions = {
  target: `http://${s3rverHostname}:${s3rverPort}`,
  changeOrigin: true,
};

const proxy = createProxyMiddleware(proxyOptions);
const serverPort = 3000;

const server = http.createServer((req, res) => {  proxy(req, res);  });

server.listen(serverPort, () => {  console.log(`Redirect server listening on port ${serverPort}`);  });