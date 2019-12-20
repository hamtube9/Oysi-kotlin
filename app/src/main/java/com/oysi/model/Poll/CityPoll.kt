package com.oysi.model.Poll

import android.os.Parcel
import android.os.Parcelable

data class CityPoll (var city : String?=null ,var state : String?=null,var country : String?=null,var care : Int?=null) : Parcelable {
 constructor(parcel: Parcel) : this(
  parcel.readString(),
  parcel.readString(),
  parcel.readString(),
  parcel.readValue(Int::class.java.classLoader) as? Int
 ) {
 }

 override fun writeToParcel(parcel: Parcel, flags: Int) {
  parcel.writeString(city)
  parcel.writeString(state)
  parcel.writeString(country)
  parcel.writeValue(care)
 }

 override fun describeContents(): Int {
  return 0
 }

 companion object CREATOR : Parcelable.Creator<CityPoll> {
  override fun createFromParcel(parcel: Parcel): CityPoll {
   return CityPoll(parcel)
  }

  override fun newArray(size: Int): Array<CityPoll?> {
   return arrayOfNulls(size)
  }
 }
}