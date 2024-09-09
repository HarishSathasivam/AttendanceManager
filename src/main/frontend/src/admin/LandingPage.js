import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AllUserTable from './AllUserTable'
import '../css/main.css'
const LandingPage = ({ currentUser,pageControl} ) => {
    const [currentPage, setCurrentPage] = useState('landing');
    const [newCurrentUser , setNewCurrentUser] = useState()
    const [showStartSessionBtn ,setShowStartSessionBtn] = useState(true);
	let interval = null;

    useEffect(() => {
       setNewCurrentUser([currentUser])
	   fetchDataForLoadPage()
	   if(!showStartSessionBtn){
		    interval = setInterval(() => {
			if (currentUser && currentUser.userName !== undefined  ) {
				fetchDataForLoadPage();
			}
			return () => clearInterval(interval);
		}, 10000);
	   }
	    
    }, [currentUser,showStartSessionBtn]);

    let endSession = ()=>{
        const url = `http://localhost:8080/AttendanceManager/logOut?userId=${currentUser.userId}`;
        axios.get(url)
          .then((response) => {
            if (response.status === 200) {
              setShowStartSessionBtn(true)  
            }
          })
          .catch((error) => {
            console.log("error = ", error);
          });
    }

    const handleLoadPage = (e) => {
      const targetPage = e.target.getAttribute('name');
      setCurrentPage(targetPage);
  }

  const handleGoBack = () => {
	    setCurrentPage('landing');
  }

  const handleStartSession = ()=>{
      userLoggedIn(currentUser);
  }


  const userLoggedIn = async () => {
    const url = `http://localhost:8080/AttendanceManager/logIn?userId=${currentUser.userId}`;
    try {
        const response = await axios.get(url);
        if (response.status === 200) {
           setShowStartSessionBtn(false)
        }
    } catch (error) {
        alert(error);
    }
}

const handleLogOut =() =>{
	pageControl('logIn')
}



const fetchDataForLoadPage = () => {
    const url = `http://localhost:8080/AttendanceManager/checkPage?userId=${currentUser.userId}`;
    axios.get(url)
      .then((response) => {
        if (response.status === 200) {
          if(response.data == "loggedIn"){
			setShowStartSessionBtn(false);
          }else if (response.data == "" || response.data == "loggedOut" ){   
			clearInterval(interval)        
			setShowStartSessionBtn(true);
			
          }         

        }
      })
      .catch((error) => {
        console.log("error = ", error);
      });
  };

    return (
      
        <div >
			 {currentPage === 'landing' && (
				<div className="welcome-container">
						

           {showStartSessionBtn ?
            
           <button
						 onClick={handleStartSession} 
						 className='StartSession'
						 style={{ cursor: 'pointer', width: '100px', height: '30px' ,background:"#767676" }}
						 >Start Session</button>
             :
                <div>
                  <h1>Hi {currentUser.userName} .......</h1>
                  <h1>Successfully Session Started </h1>

                  <button
                  onClick={endSession} 
                  className='logout1'
                  style={{ cursor: 'pointer', width: '100px', height: '30px' ,background:"#767676" }}
                  >End Session</button>
                </div>             
              }

				<button
					name="loadUserInfo"
					onClick={handleLoadPage} 
					style={{ cursor: 'pointer', width: '100px', height: 'auto' ,background:"#767676" }}
				>My Attendance</button>

				<button  onClick={handleLogOut} 
				style={{ cursor: 'pointer', width: '100px', height: '30px' ,background:"#767676" ,position:'absolute', top:'10px',left:'1900px'}}
				>
			  	logout
				</button>
				
		</div>
			 )}
           { currentPage == "loadUserInfo" && 
			
			<div>
				 <button className = "backBtn" onClick={handleGoBack} style={{ marginBottom: '20px', cursor: 'pointer' }}>Back</button>
				<AllUserTable allUsers = {newCurrentUser} />
			</div>}
      </div>
    );
}

export default LandingPage;
