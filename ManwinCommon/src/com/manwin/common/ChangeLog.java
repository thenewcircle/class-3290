package com.manwin.common;

import android.os.Parcel;
import android.os.Parcelable;

public class ChangeLog implements Parcelable {
	private String description;
	private int targetVersion;
	
	public ChangeLog(String description, int targetVersion) {
		super();
		this.description = description;
		this.targetVersion = targetVersion;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTargetVersion() {
		return targetVersion;
	}
	public void setTargetVersion(int targetVersion) {
		this.targetVersion = targetVersion;
	}
	
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(description);
		dest.writeInt(targetVersion);
	}
 
	public static final Parcelable.Creator<ChangeLog> CREATOR = new Parcelable.Creator<ChangeLog>() {

		@Override
		public ChangeLog createFromParcel(Parcel source) {
			return new ChangeLog( source.readString(), source.readInt() );
		}

		@Override
		public ChangeLog[] newArray(int size) {
			return new ChangeLog[size];
		}
	};
	
}
