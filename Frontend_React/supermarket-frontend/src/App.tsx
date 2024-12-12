import './App.css'

import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Home from './pages/Home'
import Profile from './pages/Profile'
// import Category from './pages/Category'


function App() {
  return(
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/profile' element={<Profile/>}/>
        {/* <Route path='/category' element={<Category/>}/> */}
        <Route></Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
