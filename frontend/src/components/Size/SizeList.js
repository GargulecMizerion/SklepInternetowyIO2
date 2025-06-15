import React from 'react';
import { Table, Popconfirm } from 'antd';
import { TableWrapper, DeleteButton } from '../shared/styles';

const SizeList = ({ sizes, onDelete }) => {
    const handleDelete = async (id) => {
        await onDelete(id);
    };

    const columns = [
        { title: 'Size ID', dataIndex: 'id', key: 'id'},
        { title: 'Size', dataIndex: 'sizeValue', key: 'sizeValue' },
        { title: 'Category', dataIndex: 'category', key: 'category', render: (category) => category ? category.name : 'N/A' },
        {
            title: 'Actions',
            key: 'actions',
            render: (_, record) => (
                <Popconfirm
                    title="Are you sure you want to delete this size?"
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
            <Table dataSource={sizes} columns={columns} rowKey="id" />
        </TableWrapper>
    );
};

export default SizeList;
