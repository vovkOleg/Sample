package mock.brains.mvvmappskeleton.data.remote.request

import com.google.gson.annotations.SerializedName

data class AuthRequest(

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)