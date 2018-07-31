<template>
  <div ref="gantt"></div>
</template>

<script>
/* eslint-disable */
import 'dhtmlx-gantt'
export default {
  name: 'gantt',
  props: {
    tasks: {
      type: Object,
      default () {
        return {data: [], links: []}
      }
    }
  },
  mounted () {
    console.log(gantt)
    gantt.attachEvent('onTaskSelected', (id) => {
      let task = gantt.getTask(id)
      this.$emit('task-selected', task)
    })
    gantt.attachEvent('onAfterTaskAdd', (id, task) => {
      this.$emit('task-updated', id, 'inserted', task)
      task.progress = task.progress || 0
      if (gantt.getSelectedId() === id) {
        this.$emit('task-selected', task)
      }
    })
    gantt.attachEvent('onAfterTaskUpdate', (id, task) => {
      this.$emit('task-updated', id, 'updated', task)
    })
    gantt.attachEvent('onAfterTaskDelete', (id) => {
      this.$emit('task-updated', id, 'deleted')
      if (!gantt.getSelectedId()) {
        this.$emit('task-selected', null)
      }
    })
    gantt.attachEvent('onAfterLinkAdd', (id, link) => {
      this.$emit('link-updated', id, 'inserted', link)
    })
    gantt.attachEvent('onAfterLinkUpdate', (id, link) => {
      this.$emit('link-updated', id, 'updated', link)
    })
    gantt.attachEvent('onAfterLinkDelete', (id, link) => {
      this.$emit('link-updated', id, 'deleted')
    })
    gantt.config.api_date = '%Y-%m-%d'
    gantt.config.task_date = '%Y-%m-%d'
    gantt.config.date_grid = "%Y-%m-%d"

    gantt.templates.grid_date_format = function(date){
      return gantt.date.date_to_str(gantt.config.date_grid)(date)
    }
    gantt.init(this.$refs.gantt)
    gantt.parse(this.tasks)
  }
}
</script>

<style>
@import 'dhtmlx-gantt/codebase/dhtmlxgantt.css';
</style>
