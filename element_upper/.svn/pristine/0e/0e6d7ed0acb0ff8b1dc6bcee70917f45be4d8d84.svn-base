/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
// see http://vuejs-templates.github.io/webpack for documentation.
var path = require('path')

module.exports = {
  build: {
    env: require('./prod.env'),
    index: path.resolve(__dirname, '../dist/index.html'),
    assetsRoot: path.resolve(__dirname, '../dist'),
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    productionSourceMap: true,
    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],
    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report
  },
  dev: {
    env: require('./dev.env'),
    port: 9090,
    autoOpenBrowser: true,
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: {
        '/raiis': {
        // target: 'http://192.168.1.120:8080/raiis',
        // target: 'http://192.168.1.121:8080/raiis',
        target: 'http://10.1.16.64:8080/raiis',
        // target: 'http://10.1.16.64:8080/raiis',
        // target: 'http://172.168.2.38:8080/raiis',
        // target: 'http://172.168.2.24:8080/raiis',
        // target: 'http://127.0.0.1:8080/raiis',
        changeOrigin: true,
        pathRewrite: {
        '^/raiis/': ''
        }
      },
      '/kong': {
        target: 'http://10.1.16.42:8400',
        changeOrigin: true,
        pathRewrite: {
        '^/kong/': ''
        }
      }

    },
    // CSS Sourcemaps off by default because relative paths are "buggy"
    // with this option, according to the CSS-Loader README
    // (https://github.com/webpack/css-loader#sourcemaps)
    // In our experience, they generally work as expected,
    // just be aware of this issue when enabling this option.
    cssSourceMap: false
  }
}
