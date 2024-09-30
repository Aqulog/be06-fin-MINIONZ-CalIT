import {ref} from "vue";
import axios from "axios";
import { defineStore } from 'pinia';
// import { labelData } from '@/static/labelData';
import { labelColorPalette } from '@/utils/labelUtils';

export const useTaskLabelStore = defineStore('labelStore', () => {
    const labels = ref([]);
    const labelColors = ref(labelColorPalette);

    const addTaskLabel = async ({workspaceId, labelName, description, color}) => {
        try {
            const response = await axios.post('/api/label/task', {workspaceId, labelName, description, color});
            labels.value.push(response.data);
        }
        catch (error) {
            console.error('Error adding label:', error);
        }
    }

    const getTaskLabel = async (workspaceId) => {
        try {
            const response = await axios.get(`/api/label/task?id=${workspaceId}`);
            labels.value = response.data;
        }
        catch (error) {
            console.error('Error fetching labels:', error);
        }
    };

    const updateLabel = async ({taskLabelId, labelName, description}) => {
        try {
            const response = await axios.put(`/api/label?id=${taskLabelId}`, {taskLabelId, labelName, description});
            labels.value = response.data;
        }
        catch (error) {
            console.error('Error updating label:', error);
        }
    };

    const deleteLabel = async (taskLabelId) => {
        try {
            await axios.delete(`/api/label?id=${taskLabelId}`);
            labels.value = labels.value.filter(label => label.id !== taskLabelId);
        }
        catch (error) {
            console.error('Error deleting label:', error);
        }
    };

    return {
        labels,
        labelColors,
        addTaskLabel,
        getTaskLabel,
        updateLabel,
        deleteLabel
    }
});