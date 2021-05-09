package spaceLaunch.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpaceListResponse(
    val links: Links,
    val success: Boolean,
    val name: String,
    val date_local: String,
): Parcelable

@Parcelize
data class Links(
    val patch: Patch
): Parcelable

@Parcelize
data class Patch(
    val small: String,
    val large: String,
): Parcelable