package capital.novum.concordia.model

data class Country(val countryId: Int, val country: String)
data class Citizenship(val nationalityId: Int, val nationality: String)

class Nationality : Result() {
    var citizenships: List<Citizenship>? = listOf()
    var countries: List<Country>? = listOf()
}