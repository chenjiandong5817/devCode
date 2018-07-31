const path = require('path')
const webpack = require('webpack')

module.exports = {
  entry: {
    vendor: ['vue/dist/vue.esm.js', 'vue-router', 'axios', 'vuex', 'vue-amap', 'js-md5', 'better-scroll']
  },
  output: {
    path: path.join(__dirname, '../static/js'),
    filename: '[name].dll.js',
    library: '[name]_library'       // vendor.dll.js中暴露出的全局变量名
  },
  plugins: [
    new webpack.DllPlugin({
      path: path.join(__dirname, '../static/js', 'vendor-manifest.json'),
      name: '[name]_library',
      context: __dirname
    }),
    new webpack.optimize.UglifyJsPlugin({
      compress: {
        warnings: true
      }
    })
  ]
}
