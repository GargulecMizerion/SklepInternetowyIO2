import axios from 'axios';

export const getAllCategories = async () => {
    const response = await axios.get('http://localhost:8080/category');
    return response.data;
};

export const getCategoryById = async (id) => {
    const response = await axios.get(`http://localhost:8080/category/${id}`);
    return response.data;
};

export const addCategory = async (categoryRequest) => {
    const response = await axios.post('http://localhost:8080/category', categoryRequest);
    return response.data;
};

export const deleteCategoryById = async (id) => {
    await axios.delete(`http://localhost:8080/category/${id}`);
};
