import axios from 'axios';

export const getAllProducts = async () => {
    const response = await axios.get('http://localhost:8080/product');
    return response.data;
};

export const getProductById = async (id) => {
    const response = await axios.get(`http://localhost:8080/product/${id}`);
    return response.data;
};

export const getProductByCategoryId = async (id) => {
    const response = await axios.get(`http://localhost:8080/product/category/${id}`);
    return response.data;
};

export const addProduct = async (productRequest) => {
    const response = await axios.post('http://localhost:8080/product', productRequest);
    return response.data;
};

export const deleteProductById = async (id) => {
    await axios.delete(`http://localhost:8080/product/${id}`);
};
