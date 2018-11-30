package capital.novum.concordia.model

/**
 *  Coin Market
 */

data class Money (val price: Double)

data class Quote (
        val BTC: Money,
        val XLM: Money,
        val USD: Money
)

data class ETH (val quote: Quote)

data class Data (val ETH: ETH)

data class Status(val errorCode: Int, val errorMessage: String)

data class CoinMarketResult(val data: Data, val status: Status)