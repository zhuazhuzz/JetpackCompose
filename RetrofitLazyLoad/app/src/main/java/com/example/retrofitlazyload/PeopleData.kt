package com.example.retrofitlazyload

data class ApiResponse(
    val results: List<Result>,
    val info: Info
)

data class Result(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: Dob,
    val registered: Registered,
    val phone: String,
    val cell: String,
    val id: Id,
    val picture: Picture,
    val nat: String
)

data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Any,
    val coordinates: Coordinates,
    val timezone: Timezone
)

data class Street(
    val number: Int,
    val name: String
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Timezone(
    val offset: String,
    val description: String
)

data class Login(
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
)

data class Dob(
    val date: String,
    val age: Int
)

data class Registered(
    val date: String,
    val age: Int
)

data class Id(
    val name: String,
    val value: String?
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

data class Info(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)

fun returnAPerson(): Result {
    return Result(
        gender = "Female",
        name = Name(
            title = "Miss",
            first = "Magdalena",
            last = "Hidalgo"
        ),
        location = Location(
            street = Street(
                number = 7274,
                name = "Calle de La Democracia"
            ),
            city = "La Coruña",
            state = "La Rioja",
            country = "Spain",
            postcode = 10522,
            coordinates = Coordinates(
                latitude = "37.7153",
                longitude = "104.7192"
            ),
            timezone = Timezone(
                offset = "+1:00",
                description = "Brussels, Copenhagen, Madrid, Paris"
            )
        ),
        email = "william.strong@my-own-personal-domain.com",
        login = Login(
            uuid = "963eb86c-00dd-47a5-bbf6-ccd076d88e01",
            username = "greenpeacock781",
            password = "weston",
            salt = "a3ZEPtnQ",
            md5 = "fb008592ab4aacc77b041ccac9a72347",
            sha1 = "ffaa3842c0c54fac998df03e1bb59a8ba6536181",
            sha256 = "ba3c250f69cbe77592e23eb91526ba0e62c985ce8140dddc21bcbd367ec3fa7b"
        ),
        dob = Dob(
            date = "1993-04-08T04:53:44.183Z",
            age = 31
        ),
        registered = Registered(
            date = "1993-04-08T04:53:44.183Z",
            age = 22
        ),
        phone = "718971897",
        cell = "2983467982374",
        id = Id(name = "Bobby", value = "dawdawdad"),
        picture = Picture(
            large = "https://randomuser.me/api/portraits/women/44.jpg",
            medium = "https://randomuser.me/api/portraits/med/women/44.jpg",
            thumbnail = "https://randomuser.me/api/portraits/thumb/women/44.jpg"
        ),
        nat = "ES"
    )
}

