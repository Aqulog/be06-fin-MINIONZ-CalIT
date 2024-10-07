import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import {weekSettingUtils} from "@/utils/weekSettingUtils";

export const useWorkspaceDashboardStore = defineStore('mypageStore', () => {
    const workspaceSprintData = ref([]);
    const workspaceMonthlyData = ref([]);
    const workspaceWeeklyData = ref([]);
    const workspaceDashboardData = ref([]);

    const getWorkspaceKanban = async (workspaceId) => {
        const response = await axiosInstance.get(`/api/sprint/all/${workspaceId}`, {withCredentials: true});
        workspaceSprintData.value = response.data.result;
    };

    const getWorkspaceMonthly = async (workspaceId) => {
        const { startDate, endDate } = weekSettingUtils();
        const response = await axiosInstance.get(`/api/schedule/${workspaceId}/monthly?startDate=${startDate}&endDate=${endDate}`);
        workspaceMonthlyData.value = response.data.result;
    }

    const getWorkspaceWeekly = async (workspaceId) => {
        const { startDate, endDate } = weekSettingUtils();
        const response = await axiosInstance.get(`/api/schedule/${workspaceId}/weekly?startDate=${startDate}&endDate=${endDate}`);
        workspaceWeeklyData.value = response.data.result;
    }

    const getWorkspaceDashboard = async (workspaceId) => {
        const response = await axiosInstance.get(`/api/dashboard/${workspaceId}`);
        workspaceDashboardData.value = {
            progress: {
                sprintCount: response.data.result.sprintCount,
                alltaskCount: response.data.result.alltaskCount,
                successtaskCount: response.data.result.successtaskCount,
                issueCount: response.data.result.issueCount
            },
            upcomingMeetings: response.data.result.upcomingMeetings
        };
    }

    return {
        workspaceSprintData,
        getWorkspaceKanban,
        getWorkspaceMonthly,
        getWorkspaceWeekly,
        getWorkspaceDashboard
    };
})
