import axios from 'axios';

export const getAllColors = async () => {
    const response = await axios.get('http://localhost:8080/colors');
    return response.data;
};

export const getColorById = async (id) => {
    const response = await axios.get(`http://localhost:8080/colors/${id}`);
    return response.data;
};

export const addColor = async (colorRequest) => {
    const response = await axios.post('http://localhost:8080/colors', colorRequest);
    return response.data;
};

export const deleteColorById = async (id) => {
    await axios.delete(`http://localhost:8080/colors/${id}`);
};
