import axios from 'axios';

export const getAllSizes = async () => {
    const response = await axios.get('http://localhost:8080/sizes');
    return response.data;
};

export const getSizeById = async (id) => {
    const response = await axios.get(`http://localhost:8080/sizes/${id}`);
    return response.data;
};

export const addSize = async (sizeRequest) => {
    const response = await axios.post('http://localhost:8080/sizes', sizeRequest);
    return response.data;
};

export const deleteSizeById = async (id) => {
    await axios.delete(`http://localhost:8080/sizes/${id}`);
};
