import React from 'react';
import { Table, Popconfirm } from 'antd';
import { TableWrapper, DeleteButton } from '../shared/styles';

const ColorList = ({ colors, onDelete }) => {
    const handleDelete = async (id) => {
        await onDelete(id);
    };

    const columns = [
        { title: 'Color ID', dataIndex: 'id', key: 'id'},
        { title: 'Color', dataIndex: 'color', key: 'color' },
        {
            title: 'Actions',
            key: 'actions',
            render: (_, record) => (
                <Popconfirm
                    title="Are you sure you want to delete this color?"
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
            <Table dataSource={colors} columns={columns} rowKey="id" />
        </TableWrapper>
    );
};

export default ColorList;
