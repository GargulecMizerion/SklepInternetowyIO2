import React from 'react';
import { Table, Popconfirm } from 'antd';
import { TableWrapper, DeleteButton } from '../shared/styles';

const CategoryList = ({ categories, onDelete }) => {
    const handleDelete = async (id) => {
        await onDelete(id);
    };

    const columns = [
        { title: 'Category ID', dataIndex: 'id', key: 'id' },
        { title: 'Category Name', dataIndex: 'name', key: 'name' },
        { title: 'Parent Category', dataIndex: 'parentCategory', key: 'parentCategory', render: (parentCategory) => parentCategory ? parentCategory.name : 'N/A' },
        {
            title: 'Actions',
            key: 'actions',
            render: (_, record) => (
                <Popconfirm
                    title="Are you sure you want to delete this category?"
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
            <Table dataSource={categories} columns={columns} rowKey="id" />
        </TableWrapper>
    );
};

export default CategoryList;
