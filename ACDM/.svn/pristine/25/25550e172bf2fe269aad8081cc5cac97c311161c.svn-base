<template>
  <el-row :gutter="10"  type="flex" justify="center" class="homeTop">
    <el-col :span="9" class="marginD">
      <div class="grid-content">
        <p class="title">正常率概要</p>
        <div class="flexDiv  box">
          <div class="flexS">
            <span>起飞正常率</span>
            <div class="numberFlex">
              <span class="bigText">40</span>
              <span class="percentStyle" style="color:#ecc219;">%</span>
            </div>
            <div class="progress_bar">
              <div class="bar_inner"></div>
            </div>
          </div>
          <div class="flexS">
            <span>旅行正常率</span>
            <div class="numberFlex">
              <span  class="bigText">40</span>
              <span class="percentStyle"  style="color:#00cb8f;">%</span>
            </div>
            <div class="progress_bar">
                <div class="bar_inner green_inner"></div>
            </div>
          </div>
          <div class="flexS">
            <span>平均延误</span>
            <div class="numberFlex">
              <span  class="bigText">40</span>
            </div>
            <div class="progress_bar visibilityH">
              <div class="bar_inner green_inner"></div>
            </div>
          </div>
          <div class="flexS">
            <span>厦航平均过站</span>
            <div class="numberFlex">
              <span  class="bigText">40</span>
            </div>
            <div class="progress_bar visibilityH">
              <div class="bar_inner green_inner"></div>
            </div>
          </div>
          <div class="flexS">
            <span>机场平均过站</span>
            <div class="numberFlex">
              <span  class="bigText">40</span>
            </div>
            <div class="progress_bar visibilityH">
              <div class="bar_inner green_inner"></div>
            </div>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :span="4" class="marginD">
      <div class="grid-content">
        <p class="title">最近起降航班</p>
        <div class="flexDiv  box minlineheight" style="justify-content: space-around;">
          <div class="flexS">
            <span>进港人数</span>
            <div class="numberFlex">
              <span class="middleText">40</span>
            </div>
            <span class="minText">5分钟前</span>
          </div>
          <div class="flexS">
            <span>出港人数</span>
            <div class="numberFlex">
              <span class="middleText">40</span>
            </div>
            <span class="minText">5分钟前</span>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :span="11" class="marginD">
      <div class="grid-content">
        <p class="title">进出架次/人数统计</p>
        <div class="flexDiv   box" >
          <div class="flexS">
            <span>总架次</span>
            <div class="numberFlex">
              <span  class="bigText">4000</span>
            </div>
            <div class="progress_bar visibilityH">
              <div class="bar_inner green_inner"></div>
            </div>
          </div>
          <div class="flexS">
            <span>进港架次</span>
            <div class="numberFlex">
              <span  class="bigText">409</span>
            </div>
            <div class="progress_bar visibilityH">
              <div class="bar_inner green_inner"></div>
            </div>
          </div>
          <div class="flexS">
            <span>出港架次</span>
            <div class="numberFlex">
              <span  class="bigText">406</span>
            </div>
            <div class="progress_bar visibilityH">
              <div class="bar_inner green_inner"></div>
            </div>
          </div>
          <div class="flexS">
            <span>吞吐量</span>
            <div class="numberFlex">
              <span  class="bigText">4055</span>
            </div>
            <div class="progress_bar visibilityH">
              <div class="bar_inner green_inner"></div>
            </div>
          </div>
          <div class="flexS">
            <span>进港人数</span>
            <div class="numberFlex">
              <span  class="bigText">4044</span>
            </div>
            <div class="progress_bar visibilityH">
              <div class="bar_inner green_inner"></div>
            </div>
          </div>
          <div class="flexS">
            <span>出港人数</span>
            <div class="numberFlex">
              <span  class="bigText">4033</span>
            </div>
            <div class="progress_bar visibilityH">
              <div class="bar_inner green_inner"></div>
            </div>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>
<style>
.minlineheight {
  line-height:25px;
}
.homeTop .bigText{
  font-size: 28px;
}
.homeTop .middleText{
  font-size: 24px;
}
.percentStyle{
  display: inline-block;
  vertical-align: top;
}
.homeTop{
    height: 20%;
}
.homeTop .title {
    font-size: 14px;
    color: #bec5d2;
    font-weight: bold
}
.homeTop .grid-content{
  font-size: 12px;
  color: #949499;
}
.homeTop .bigText{
  font-family: ArialMT;
  letter-spacing: 1px;
  color: #bec5d2;
}
.homeTop .middleText{
  font-family: ArialMT;
  letter-spacing: 1px;
  color: #bec5d2;
}
.flexDiv{
    display: flex;
    justify-content: space-between;
    padding: 0 15px;
    height: calc(100% - 30px);
    padding: 5px;
    box-sizing: border-box;
}
.minText{
  color: #33bf95;
  font-size: 12px
}
.marginD{
  height: 100%;
}
.progress_bar {
    width: 100%;
    height: 4px;
    background-color: #e6e6e6;
    border-radius: 5px;
}
.bar_inner {
    height: 4px;
    border-radius: 5px;
    width: 50%;
    background: #ecc219;
}
.green_inner{
  background:#58bd9f
}
.homeTop .grid-content{
  height: 100%;
}
.flexS{
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  padding: 6px 0;
}
.numberFlex{
  display: flex;
  justify-content: center;
  align-items: center;
}
.visibilityH{
  visibility:hidden;
}
@media screen and (max-width: 1439px) {
  .minlineheight {
    line-height: 18px;
  }
  .homeTop .bigText {
      font-size: 24px;
  }
  .homeTop .middleText{
    font-size: 20px;
  }
  .percentStyle {
    padding-top:0
  }
}

</style>
