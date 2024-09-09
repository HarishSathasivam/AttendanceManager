import React, { useMemo, useState } from 'react';
import { format, differenceInSeconds, differenceInMinutes } from 'date-fns';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
import '../css/tableCss.css'; // Import custom CSS file

const MyTable = ({ allUsers }) => {
    const [filterInput, setFilterInput] = useState("");
    const [startDateTime, setStartDateTime] = useState("");
    const [endDateTime, setEndDateTime] = useState("");
    const [sortConfig, setSortConfig] = useState({ key: 'logedInTime', direction: 'asc' });
    let lastLogoutTime = "";
    // Group data by date and calculate total duration for each user
    const dataByDate = useMemo(() => {
        const groupedData = {};

        allUsers.forEach(user => {
           
            user.userAction.forEach(action => {
                const loggedInDate = format(new Date(action.loggdInTime), 'yyyy-MM-dd');
                const logedInTime = new Date(action.loggdInTime);
                const logedOutTime = action.loggedOutTime ? new Date(action.loggedOutTime) : null;

                // Calculate the duration in seconds
                const durationInSeconds = logedOutTime ? differenceInSeconds(logedOutTime, logedInTime) : 0;

                if (!groupedData[loggedInDate]) {
                    groupedData[loggedInDate] = [];
                }

                // Check if the user logged in again within an hour of logging out
                const sameDayLogout =
                lastLogoutTime &&
                format(logedInTime, "yyyy-MM-dd") ===
                  format(lastLogoutTime, "yyyy-MM-dd");
      
            //   const reLoginWithinHour =
            //     sameDayLogout && differenceInMinutes(logedInTime, lastLogoutTime) <= 60;
      
                
      
            //   console.log(
            //     "differenceInMinutes = " +
            //       differenceInMinutes(logedInTime, lastLogoutTime) +
            //       " logedInTime = ",
            //     logedInTime,
            //     "lastLogoutTime =",
            //     lastLogoutTime
            //   );

                groupedData[loggedInDate].push({
                    userName: user.userName,
                    logedInTime: action.loggdInTime,
                    logedOutTime: action.loggedOutTime,
                    duration: durationInSeconds,
                    userRole: user.userRole,
                  
                });

                // Update the last logout time
                if (logedOutTime) {
                    lastLogoutTime = logedOutTime;
                }
            });
        });

        return groupedData;
    }, [allUsers]);

    const formatDuration = (seconds) => {
        if (seconds === null) return '';
        const hours = Math.floor(seconds / 3600);
        const minutes = Math.floor((seconds % 3600) / 60);
        const remainingSeconds = seconds % 60;

        return `${hours}h ${minutes}m ${remainingSeconds}s`;
    };

    const reLoginWithinHour = (user) =>{
        let logedInTime = new Date(user.logedInTime) ;
        let logedOutTime =  new Date(user.logedOutTime);
       //console.log(user.userName,'logedInTime ',logedInTime,'logedOutTime ',logedOutTime,'lastLogoutTime )))) ',new Date(lastLogoutTime));
        let frequent_login = differenceInMinutes(logedInTime, new Date(lastLogoutTime)) <= 55;
       //console.log("frequent_login = ",frequent_login,differenceInMinutes(logedInTime, new Date(lastLogoutTime)))
        lastLogoutTime = user.logedOutTime;

          return frequent_login  ?  "row-highlight-relogin" :  user.duration >= 4800 ? 'row-highlight-notLoggedOut' : '';
    
    }

    const columns = useMemo(
        () => [
            { Header: 'Username', accessor: 'userName' },
            {
                Header: 'LoggedIn Time',
                accessor: 'logedInTime',
                Cell: ({ value }) => value ? format(new Date(value), 'yyyy-MM-dd HH:mm:ss') : 'N/A'
            },
            {
                Header: 'LoggedOut Time',
                accessor: 'logedOutTime',
                Cell: ({ value }) => value ? format(new Date(value), 'yyyy-MM-dd HH:mm:ss') : 'N/A'
            },
            {
                Header: 'Duration',
                accessor: 'duration',
                Cell: ({ value }) => formatDuration(value)
            },
            { Header: 'Role', accessor: 'userRole' },
        ],
        []
    );

    // Filtered data based on datetime range
    const filteredData = useMemo(() => {
        if (!startDateTime && !endDateTime) return dataByDate;
        const start = startDateTime ? new Date(startDateTime) : null;
        const end = endDateTime ? new Date(endDateTime) : null;

        const filtered = {};

        Object.keys(dataByDate).forEach(date => {
            if (start && new Date(date) < start) return;
            if (end && new Date(date) > end) return;

            filtered[date] = dataByDate[date];
        });

        return filtered;
    }, [dataByDate, startDateTime, endDateTime]);

    const sortedFilteredData = useMemo(() => {
        const sortedData = Object.keys(filteredData)
            .sort((a, b) => new Date(a) - new Date(b)) // Sort dates
            .reduce((acc, key) => {
                acc[key] = filteredData[key];
                return acc;
            }, {});

        // Sort users within each date based on sortConfig
        Object.keys(sortedData).forEach(date => {
            const usersForDate = sortedData[date];
            sortedData[date] = [...usersForDate].sort((a, b) => {
                if (a[sortConfig.key] < b[sortConfig.key]) {
                    return sortConfig.direction === 'asc' ? -1 : 1;
                }
                if (a[sortConfig.key] > b[sortConfig.key]) {
                    return sortConfig.direction === 'asc' ? 1 : -1;
                }
                return 0;
            });
        });

        return sortedData;
    }, [filteredData, sortConfig]);

    const generatePDF = () => {
        const input = document.getElementById('table-to-print');
        html2canvas(input, {
            scale: 1, // Reduce scale for smaller file size
            useCORS: true
        }).then((canvas) => {
            const imgData = canvas.toDataURL('image/jpeg', 0.8); // Compress image
            const pdf = new jsPDF('p', 'pt', 'a4');
            const imgWidth = pdf.internal.pageSize.getWidth();
            const pageHeight = pdf.internal.pageSize.getHeight();
            const imgHeight = (canvas.height * imgWidth) / canvas.width;
            let heightLeft = imgHeight;
            let position = 0;

            // Add the first page
            pdf.addImage(imgData, 'JPEG', 0, position, imgWidth, imgHeight, '', 'FAST');
            heightLeft -= pageHeight;

            // Add remaining pages if needed
            while (heightLeft >= 0) {
                position = heightLeft - imgHeight;
                pdf.addPage();
                pdf.addImage(imgData, 'JPEG', 0, position, imgWidth, imgHeight, '', 'FAST');
                heightLeft -= pageHeight;
            }

            pdf.save('attendance.pdf');
        }).catch((error) => {
            console.error("Error generating PDF:", error);
        });
    };

    const handleFilterChange = e => {
        setFilterInput(e.target.value);
    };

    const handleStartDateTimeChange = e => {
        setStartDateTime(e.target.value);
    };

    const handleEndDateTimeChange = e => {
        setEndDateTime(e.target.value);
    };

    const handleSort = (key) => {
        let direction = 'asc';
        if (sortConfig.key === key && sortConfig.direction === 'asc') {
            direction = 'desc';
        }
        setSortConfig({ key, direction });
    };

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
                    <input
                        type="datetime-local"
                        value={startDateTime}
                        onChange={handleStartDateTimeChange}
                        placeholder={"Start DateTime"}
                        className="form-control"
                        style={{ width: '200px' }}
                    />
                    <input
                        type="datetime-local"
                        value={endDateTime}
                        onChange={handleEndDateTimeChange}
                        placeholder={"End DateTime"}
                        className="form-control"
                        style={{ width: '200px' }}
                    />
                    <button className="PDFbtn" onClick={generatePDF}>
                        Generate PDF
                    </button>
                </div>
            </div>
            <div id="table-to-print">
                <div className="mb-4">
                    <table className="table">
                        <thead className="thead-dark">
                            <tr>
                                {columns.map(column => (
                                    <th
                                        key={column.accessor}
                                        onClick={() => handleSort(column.accessor)}
                                        style={{ cursor: 'pointer' }}
                                    >
                                        {column.Header}
                                        {sortConfig.key === column.accessor ? (
                                            sortConfig.direction === 'asc' ? ' ↑' : ' ↓'
                                        ) : null}
                                    </th>
                                ))}
                            </tr>
                        </thead>
                    </table>
                </div>
                {Object.keys(sortedFilteredData).map(date => {
                    const usersForDate = sortedFilteredData[date]
                        .filter(user => user.userName.toLowerCase().includes(filterInput.toLowerCase()));

                    // Sort usersForDate by loggdInTime
                    const sortedUsersForDate = [...usersForDate].sort((a, b) => new Date(a.logedInTime) - new Date(b.logedInTime));

                    const userDurations = sortedUsersForDate.reduce((acc, user) => {
                        if (!acc[user.userName]) {
                            acc[user.userName] = 0;
                        }
                        acc[user.userName] += user.duration;
                        return acc;
                    }, {});

                    return (
                        <div key={date} className="mb-4">
                            {/* Table Title */}
                            <h3 style={{ textAlign: 'center', marginBottom: '20px', fontSize: '24px', fontWeight: 'bold' }}>
                                Attendance Report for {date}
                            </h3>
                            <table className="table table-striped table-bordered">
                                <tbody>
                                    {sortedUsersForDate.map((user, index) => (
                                        <tr key={index} className={reLoginWithinHour (user)}>
                                            
                                          
                                            {columns.map(column => (
                                                <td key={column.accessor}>
                                                    {column.Cell ? column.Cell({ value: user[column.accessor] }) : user[column.accessor]}
                                                </td>
                                            ))}
                                        </tr>
                                    ))}
                                    <tr id="SummaryTr">
                                        <td colSpan={columns.length} style={{ textAlign: 'center', fontWeight: 'bold' }}>
                                            Summary for {date}
                                        </td>
                                    </tr>
                                    {Object.keys(userDurations).map((userName, index) => (
                                        <tr className={userDurations[userName] <= 14400 ? 'row-short-duration' : ''} key={index}>
                                            <td colSpan={columns.length}>
                                                <strong>{userName}</strong> spent: <strong>{formatDuration(userDurations[userName])}</strong>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default MyTable;
