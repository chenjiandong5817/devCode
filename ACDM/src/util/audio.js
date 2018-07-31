
import { debounce } from 'throttle-debounce'
const checkWindowAudioContext = () => {
  window.AudioContext = window.AudioContext || window.webkitAudioContext
  if (!window.AudioContext) {
    console.error('当前浏览器不支持Web Audio API')
    return false
  }
  return true
}
const createVoice = (audioCtx, frequency) => {
  // 创建新的音频上下文接口
  // var audioCtx = new AudioContext()
  // 发出的声音频率数据，表现为音调的高低
  // var arrFrequency = [196.00, 220.00, 246.94, 261.63, 293.66, 329.63, 349.23, 392.00, 440.00, 493.88, 523.25, 587.33, 659.25, 698.46, 783.99, 880.00, 987.77, 1046.50]
  // 当前频率
  // var frequency = arrFrequency[0]
  // 创建一个OscillatorNode, 它表示一个周期性波形（振荡），基本上来说创造了一个音调
  var oscillator = audioCtx.createOscillator()
  // 创建一个GainNode,它可以控制音频的总音量
  var gainNode = audioCtx.createGain()
  // 把音量，音调和终节点进行关联
  oscillator.connect(gainNode)
  // audioCtx.destination返回AudioDestinationNode对象，表示当前audio context中所有节点的最终节点，一般表示音频渲染设备
  gainNode.connect(audioCtx.destination)
  // 指定音调的类型，其他还有square|triangle|sawtooth
  oscillator.type = 'sine'
  // 设置当前播放声音的频率，也就是最终播放声音的调调
  oscillator.frequency.value = frequency
  // 当前时间设置音量为0
  gainNode.gain.setValueAtTime(0, audioCtx.currentTime)
  // 0.01秒后音量为1
  gainNode.gain.linearRampToValueAtTime(1, audioCtx.currentTime + 0.01)
  // 音调从当前时间开始播放
  oscillator.start(audioCtx.currentTime)
  // 1秒内声音慢慢降低，是个不错的停止声音的方法
  gainNode.gain.exponentialRampToValueAtTime(0.001, audioCtx.currentTime + 1)
  // 1秒后完全停止声音
  oscillator.stop(audioCtx.currentTime + 1)
}
class AudioUtil {
  constructor () {
    this.windowAudioContextEnable = checkWindowAudioContext()
    if (this.windowAudioContextEnable) {
      this.audioCtx = new AudioContext()
      // [196.00, 220.00, 246.94, 261.63, 293.66, 329.63, 349.23, 392.00, 440.00, 493.88, 523.25, 587.33, 659.25, 698.46, 783.99, 880.00, 987.77, 1046.50]
      this.arrFrequency = [220.00, 246.94, 261.63]
    }
    this.playVoice = debounce(300, function () {
      if (!this.arrFrequency) {
        console.error('无法进行声音播放')
        return false
      }
      let doubleArray = [...this.arrFrequency, ...this.arrFrequency]
      doubleArray.forEach((frequency, index) => {
        setTimeout(() => {
          createVoice(this.audioCtx, frequency)
        }, (index < this.arrFrequency.length ? index : (index + 2)) * 380)
      })
    })
  }
  voice () {
    return this.playVoice()
  }
}
const audio = new AudioUtil()
export default audio
