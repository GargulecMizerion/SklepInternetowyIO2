import axios from 'axios';

export const getAllStock = async () => {
    const response = await axios.get('http://localhost:8080/stock');
    return response.data;
};

export const getStockById = async (id) => {
    const response = await axios.get(`http://localhost:8080/stock/${id}`);
    return response.data;
};

export const getStockByProductId = async (id) => {
    const response = await axios.get(`http://localhost:8080/stock/product/${id}`);
    return response.data;
};

export const addStock = async (stockRequest) => {
    const response = await axios.post('http://localhost:8080/stock', stockRequest);
    return response.data;
};

export const deleteStockById = async (id) => {
    await axios.delete(`http://localhost:8080/stock/${id}`);
};
