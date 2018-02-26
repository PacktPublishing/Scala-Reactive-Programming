package repositories

import forms.UserData

class UserRepository {

  var userDataList = List[UserData] (
    UserData("adminone","adminone"),
    UserData("userone","userone"),
    UserData("usertwo","usertwo")
  )

  def isValidUser(user:UserData):Boolean = {
    val index = userDataList.indexOf(user)
    if(index >= 0) true
    else false
  }
}
