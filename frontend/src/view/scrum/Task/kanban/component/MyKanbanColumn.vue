<script setup>
import { computed, defineProps, watch, defineEmits } from 'vue';
import { VueDraggableNext } from 'vue-draggable-next';
import TaskCard from './MyKanbanCard.vue';
import {
  getTaskCountBackgroundColor,
  getTaskCountColor,
} from '@/utils/taskUtils';
import { useRoute } from 'vue-router';
import { useTaskStore } from '@/stores/scrum/useTaskStore';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const taskStore = useTaskStore();

const emit = defineEmits(['taskUpdated']);

const props = defineProps({
  data: {
    type: Object,
    required: true,
  },
});

const taskCountBgStyle = computed(() => {
  return getTaskCountBackgroundColor(props.data.status);
});

const taskCountColorStyle = computed(() => {
  return getTaskCountColor(props.data.status).color;
});

const status = computed(() => Object.keys(props.data)[0]);
const tasks = computed(() => Object.values(props.data)[0]);

watch(
  () => props.data.tasks,
  (newTasks) => {
    tasks.value = [...newTasks];
  }
);

const handleDragEnd = async (event) => {
  const newStatus = event.to.getAttribute('item-key');
  const taskId = event.item.getAttribute('id');

  await taskStore.updateMyTaskStatus(taskId, newStatus);

  emit('taskUpdated');
};
</script>

<template>
  <div class="task-column">
    <div class="column-header">
      <p>{{ getTaskCountColor(status).text }}</p>
      <span
        class="task-count"
        :style="{
          backgroundColor: taskCountBgStyle,
          color: taskCountColorStyle,
        }"
      >
        {{ tasks ? tasks.length : 0 }}
      </span>
    </div>

    <VueDraggableNext
      :list="tasks"
      :item-key="status"
      group="tasks"
      draggable=".task-card"
      handle=".task-card"
      @end="handleDragEnd"
    >
      <template v-if="tasks && tasks.length > 0">
        <TaskCard
          v-for="task in tasks"
          :key="task.id"
          :task="task"
          :id="task.id"
        />
      </template>
      <template v-else>
        <div class="task-card">현재 할당된 task가 없습니다.</div>
      </template>
    </VueDraggableNext>

    <router-link
      v-if="!route.path.startsWith('/my')"
      :to="`/workspace/${workspaceId}/scrum/task/create`"
      class="add-task-card"
    >
      <span class="plus">+</span> <span class="add-text">Add Task</span>
    </router-link>
  </div>
</template>

<style scoped>
.task-column {
  width: 100%;
  background-color: #fff;
  border-radius: 8px;
  padding: 10px;
}
.column-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 0 10px 5px;
  p {
    font-size: 14px;
    font-weight: 500;
  }
}
.task-count {
  padding: 0 10px;
  border-radius: 30px;
  font-size: 14px;
  font-weight: 500;
}
.add-task-card {
  background-color: #f7f8fa;
  border-radius: 8px;
  padding: 10px;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
}

.plus {
  font-weight: 700;
  background-color: #c7ced9;
  color: #fff;
  padding: 0 7px;
  border-radius: 50%;
}

.add-text {
  font-size: 12px;
}

a {
  text-decoration: none;
  color: #28303f;
}

.task-card {
  background-color: #f7f8fa;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 15px;
  font-size: 14px;
  font-weight: 500;
}

.empty-drop-zone {
  background-color: #f0f0f0;
  border: 2px dashed #c7ced9;
  border-radius: 8px;
  padding: 20px;
  text-align: center;
  color: #9e9e9e;
  margin-bottom: 15px;
}
</style>
