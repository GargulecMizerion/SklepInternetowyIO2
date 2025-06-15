import React from 'react';
import { Table, Popconfirm } from 'antd';
import { TableWrapper, DeleteButton } from '../shared/styles';

const StockList = ({ stock, onDelete }) => {
    const handleDelete = async (id) => {
        await onDelete(id);
    };

    const columns = [
        { title: 'Stock ID', dataIndex: 'id', key: 'id'},
        { title: 'Product', dataIndex: 'product', key: 'product', render: (product) => product ? product.name : 'N/A' },
        { title: 'Size', dataIndex: 'size', key: 'size', render: (size) => size ? size.sizeValue : 'N/A' },
        { title: 'Color', dataIndex: 'color', key: 'color', render: (color) => color ? color.color : 'N/A' },
        { title: 'Quantity', dataIndex: 'quantity', key: 'quantity'},
        { title: 'Price', dataIndex: 'price', key: 'price'},
        {
            title: 'Actions',
            key: 'actions',
            render: (_, record) => (
                <Popconfirm
                    title="Are you sure you want to delete this stock?"
                    onConfirm={() => handleDelete(record.id)}
                    okText="Yes"
                    cancelText="No"
                >
                    <DeleteButton>Delete</DeleteButton>
                </Popconfirm>
            ),
        },
    ];

    return (
        <TableWrapper>
            <Table dataSource={stock} columns={columns} rowKey="id" />
        </TableWrapper>
    );
};

export default StockList;
