import React, { useMemo, useState } from 'react';
import { useTable, useSortBy, useFilters, usePagination } from 'react-table';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
import '../css/tableCss.css'; // Import custom CSS file

const UsersTable = ({ newAllUser, onEditUser, onDeleteUser }) => {
    const [filterInput, setFilterInput] = useState("");
    const data = useMemo(() => {
        const transformedData = [];

        newAllUser.length > 0 && newAllUser.forEach(user => {           
            transformedData.push({
                userName: user.userName,
                frequencyIntervalToLogOut : user.frequencyIntervalToLogOut,
                userRole: user.userRole,
                actions: user // Passing the whole user object for actions
            });
        });

        return transformedData;
    }, [newAllUser]);

    const columns = useMemo(
        () => [
            { Header: 'Username', accessor: 'userName' },
            { Header: 'Frequency Interval for LogOut', accessor: 'frequencyIntervalToLogOut' },
            { Header: 'Role', accessor: 'userRole' },
            {
                Header: 'Actions',
                accessor: 'actions',
                Cell: ({ cell: { value } }) => (
                    <div>
                        <button className='editBtn' onClick={() => onEditUser(value)}>Edit</button>
                        <button className='deleteBtn' onClick={() => onDeleteUser(value)}>Delete</button>
                    </div>
                )
            }
        ],
        [onEditUser, onDeleteUser]
    );

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        prepareRow,
        page, 
        canPreviousPage,
        canNextPage,
        pageOptions,
        pageCount,
        gotoPage,
        nextPage,
        previousPage,
        setPageSize,
        state: { pageIndex, pageSize },
        setFilter,
    } = useTable(
        {
            columns,
            data,
            initialState: { pageIndex: 0, pageSize: 10 }, 
        },
        useFilters, 
        useSortBy, 
        usePagination 
    );

    const handleFilterChange = e => {
        const value = e.target.value || undefined;
        setFilter("userName", value); 
        setFilterInput(value);
    };

    ;

    return (
        <div>
            <div className="d-flex justify-content-between mb-3">
                <div className="paginationBtn">
                    <input
                        value={filterInput}
                        onChange={handleFilterChange}
                        placeholder={"Search Username"}
                        className="form-control"
                        style={{ width: '300px' }}
                    />
                    <button className="navBtn" onClick={() => gotoPage(0)} disabled={!canPreviousPage}>
                        {'first'}
                    </button>
                    <button className="navBtn" onClick={() => previousPage()} disabled={!canPreviousPage}>
                        {'Previous'}
                    </button>
                    <button className="navBtn" onClick={() => nextPage()} disabled={!canNextPage}>
                        {'Next'}
                    </button>
                    <button className="navBtn" onClick={() => gotoPage(pageCount - 1)} disabled={!canNextPage}>
                        {'Last'}
                    </button>
                    <span className="me-1">
                        Page{' '}
                        <strong>
                            {pageIndex + 1} of {pageOptions.length}
                        </strong>{' '}
                    </span>
                    <select
                        value={pageSize}
                        onChange={e => {
                            setPageSize(Number(e.target.value));
                        }}
                        className="form-select"
                        style={{ width: '100px' }}
                    >
                        {[10, 20, 30, 40, 50].map(pageSize => (
                            <option key={pageSize} value={pageSize}>
                                Show {pageSize}
                            </option>
                        ))}
                    </select>
                    
                </div>
            </div>
            <div id="table-to-print">
                <table {...getTableProps()} className="table table-striped table-bordered">
                    <thead className="thead-dark">
                        {headerGroups.map(headerGroup => (
                            <tr {...headerGroup.getHeaderGroupProps()} key={headerGroup.id}>
                                {headerGroup.headers.map(column => (
                                    <th {...column.getHeaderProps(column.getSortByToggleProps())} key={column.id}>
                                        {column.render('Header')}
                                        <span>
                                            {column.isSorted
                                                ? column.isSortedDesc
                                                    ? ' 🔽'
                                                    : ' 🔼'
                                                : ''}
                                        </span>
                                    </th>
                                ))}
                            </tr>
                        ))}
                    </thead>
                    <tbody {...getTableBodyProps()}>
                        {page.map(row => {
                            prepareRow(row);
                            return (
                                <tr {...row.getRowProps()} key={row.id}>
                                    {row.cells.map(cell => (
                                        <td {...cell.getCellProps()} key={cell.column.id}>{cell.render('Cell')}</td>
                                    ))}
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default UsersTable;
