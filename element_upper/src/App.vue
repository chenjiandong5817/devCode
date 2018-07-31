<template>
	<div id="app">
		<router-view></router-view>
	</div>
</template>

<script>
	export default {
	  name: 'app',
	  components: {}
	}

</script>

<style lang="scss">
  @import './styles/global';
	body {
		margin: 0px;
		padding: 0px;
		/*background: url(assets/bg1.jpg) center !important;
		background-size: cover;*/
		background: #1F2D3D;
		font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, SimSun, sans-serif;
		font-size: 14px;
		-webkit-font-smoothing: antialiased;
	}

	#app {
		position: absolute;
		top: 0px;
		bottom: 0px;
		width: 100%;
	}

	.el-submenu [class^=fa] {
		vertical-align: baseline;
		margin-right: $margin;
	}

	.el-menu-item [class^=fa] {
		vertical-align: baseline;
		margin-right: $margin;
	}
  .table-content {
    margin: $margin-left-right;
  }
	.toolbar {
		background: #fff;
		padding: $padding;
	}
  .toolbar-bottom {
    border: 0;
    z-index: 90;
    background: #fff;
    padding: $padding;
    position: fixed;
    bottom: 0;

    transition: width .2s;
    -moz-transition: width .2s;
    -webkit-transition: width .2s;
    -o-transition: width .2s;
  }
  .toolbar .el-form-item {
		margin-bottom: $margin;
	}

	.my-popper-class {
		border: 0px !important;
		margin-left: 0px !important;
		padding: 0px !important;
	}


	/* scroll bar */
	::-webkit-scrollbar {
		width: 14px;
		height: 14px;
	}

	::-webkit-scrollbar-track,
	::-webkit-scrollbar-thumb {
		border-radius: 999px;
		border: 5px solid transparent;
	}

	::-webkit-scrollbar-track {
		box-shadow: 1px 1px 5px rgba(0,0,0,.2) inset;
	}

	::-webkit-scrollbar-thumb {
		min-height: 20px;
		background-clip: content-box;
		box-shadow: 0 0 0 5px rgba(93, 134, 165, 1) inset;
	}

	::-webkit-scrollbar-corner {
		background: transparent;
	}
</style>
