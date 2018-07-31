import * as d3 from 'd3'

export const terminalPath = () => {
  var tPath = d3.path()
  tPath.moveTo(0, 0)
  tPath.lineTo(1000, 0)
  tPath.lineTo(1000, 600)
  tPath.lineTo(0, 600)
  tPath.moveTo(0, 0)
  tPath.lineTo(0, 600)
  tPath.moveTo(0, 300)
  tPath.lineTo(1000, 300)
  tPath.moveTo(800, 0)
  tPath.lineTo(800, 300)
  tPath.moveTo(400, 0)
  tPath.lineTo(400, 300)
  tPath.moveTo(200, 300)
  tPath.lineTo(200, 600)

  // 登机口1
  tPath.moveTo(50, 0)
  tPath.lineTo(50, 5)
  tPath.lineTo(100, 5)
  tPath.lineTo(100, 0)
  // 登机口2
  tPath.moveTo(300, 0)
  tPath.lineTo(300, 5)
  tPath.lineTo(350, 5)
  tPath.lineTo(350, 0)
  // 值机引导
  tPath.moveTo(75, 300)
  tPath.lineTo(75, 305)
  tPath.lineTo(125, 305)
  tPath.lineTo(125, 300)
  // 安检口
  tPath.moveTo(275, 300)
  tPath.lineTo(275, 305)
  tPath.lineTo(325, 305)
  tPath.lineTo(325, 300)
  // 值机柜台1
  tPath.moveTo(480, 300)
  tPath.lineTo(480, 305)
  tPath.lineTo(520, 305)
  tPath.lineTo(520, 300)
  // 值机柜台2
  tPath.moveTo(540, 300)
  tPath.lineTo(540, 305)
  tPath.lineTo(580, 305)
  tPath.lineTo(580, 300)
  // 值机柜台2
  tPath.moveTo(600, 300)
  tPath.lineTo(600, 305)
  tPath.lineTo(640, 305)
  tPath.lineTo(640, 300)
  // 航班到达
  tPath.moveTo(875, 300)
  tPath.lineTo(875, 305)
  tPath.lineTo(925, 305)
  tPath.lineTo(925, 300)
  // 行李转盘
  tPath.moveTo(1000, 125)
  tPath.lineTo(995, 125)
  tPath.lineTo(995, 175)
  tPath.lineTo(1000, 175)
  console.log(tPath.toString())
  return tPath.toString()
}
