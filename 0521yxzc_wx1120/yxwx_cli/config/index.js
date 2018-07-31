/*
* @Author: cdroid
* @Date:   2018-05-17 16:23:30
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-06-07 09:05:16
*/
'use strict'
// Template version: 1.2.5
// see http://vuejs-templates.github.io/webpack for documentation.
/*
  * dev:开发模式
  * proxyTable:服务代理过滤配置，案例如下
  * '/api': {
        target: 'http://apinternal.yueyuechuxing.cn',
        changeOrigin: true,
      }
  * build:打包配置参数
  * index:打包路口文件目录
  * assetsRoot:静态资源文件根目录
  * assetsSubDirectory:资源文件夹
  * assetsPublicPath:访问资源文件根路径
  * */
const path = require('path')

module.exports = {
  dev: {

    // Paths
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: {
      '/wechat/*': {
        // target: 'http://wxtest02.yxzc01.com/',
        target: 'http://10.0.38.143:15143/',
        //target: 'http://localhost:8090',
        // target: 'http://10.0.38.146:14146/',
        secure: false,
        changeOrigin: true
      },
      '/api/*': {
        // target: 'http://wxtest02.yxzc01.com/',
        target: 'http://10.0.38.143:15143/',
        //target: 'http://localhost:8090',
        // target: 'http://10.0.38.146:14146/',
        secure: false,
        changeOrigin: true
      }
    },

    // Various Dev Server settings
    host: 'localhost', // can be overwritten by process.env.HOST
    port: 8080, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    autoOpenBrowser: false,
    errorOverlay: true,
    notifyOnErrors: true,
    poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-

    // Use Eslint Loader?
    // If true, your code will be linted during bundling and
    // linting errors and warnings will be shown in the console.
    useEslint: true,
    // If true, eslint errors and warnings will also be shown in the error overlay
    // in the browser.
    showEslintErrorsInOverlay: false,

    /**
     * Source Maps
     */

    // https://webpack.js.org/configuration/devtool/#development
    devtool: 'eval-source-map',

    // If you have problems debugging vue-files in devtools,
    // set this to false - it *may* help
    // https://vue-loader.vuejs.org/en/options.html#cachebusting
    cacheBusting: true,

    // CSS Sourcemaps off by default because relative paths are "buggy"
    // with this option, according to the CSS-Loader README
    // (https://github.com/webpack/css-loader#sourcemaps)
    // In our experience, they generally work as expected,
    // just be aware of this issue when enabling this option.
    cssSourceMap: false
  },

  build: {
    // Template for index.html
    index: path.resolve(__dirname, '../../web/WEB-INF/pages/wxPage/index.fz.jsp'),
    userCenter: path.resolve(__dirname, '../../web/WEB-INF/pages/wxPage/userCenter.jsp'),

    // Paths
    assetsRoot: path.resolve(__dirname, '../../web'),
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',

    /**
     * Source Maps
     */

    productionSourceMap: true,
    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map',

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
  }
}
