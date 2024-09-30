import { ref } from 'vue';
import axios from "axios";
import { defineStore } from 'pinia';

export const useFriendsStore = defineStore('friendsStore', () => {
    const friends = ref([]);

    const getUserList = async (username) => {
        try {
            const response = await axios.get('/api/search/alluser', username);
            friends.value = response.data;
        } catch (error) {
            console.error('Error fetching friends:', error);
        }
    };

    const getFriendsList = async ({workspaceId, userId, name }) => {
        try {
            const response = await axios.get(`/api/friends/search?workspaceId=${workspaceId}&id=${userId}&name=${name}`);
            friends.value = response.data;
        } catch (error) {
            console.error('Error fetching friends:', error);
        }
    }

    return {
        friends,
        getUserList,
        getFriendsList
    };
});