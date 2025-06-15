import axios from 'axios';

export const getAllImages = async () => {
    const response = await axios.get('http://localhost:8080/image');
    return response.data;
};

export const getImageById = async (id) => {
    const response = await axios.get(`http://localhost:8080/image/${id}`);
    return response.data;
};

export const getImageByProductId = async (id) => {
    const response = await axios.get(`http://localhost:8080/image/product/${id}`);
    return response.data;
};

export const addImage = async (imageRequest) => {
    const response = await axios.post('http://localhost:8080/image', imageRequest);
    return response.data;
};

export const deleteImageById = async (id) => {
    await axios.delete(`http://localhost:8080/image/${id}`);
};
