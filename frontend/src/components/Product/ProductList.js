import React from 'react';
import { Table } from 'antd';
import { TableWrapper } from './styles';

const ProductList = ({ products }) => {
    const columns = [
        { title: 'Product Name', dataIndex: 'name', key: 'name' },
        { title: 'Description', dataIndex: 'description', key: 'description' },
        { title: 'Category', dataIndex: 'category', key: 'category', render: (category) => category ? category.name : 'N/A' },
    ];    

    return (
        <TableWrapper>
            <Table dataSource={products} columns={columns} rowKey="id" />
        </TableWrapper>
    );
};

export default ProductList;
