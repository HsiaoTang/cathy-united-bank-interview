# 📘 Exchange Rate API 說明文件

提供與匯率資料（Exchange Rate）相關的 CRUD 操作與轉換功能。

---

## 🧭 Base URL

```
/v1/exchangeRate
```

---

## 📡 API 一覽

| Method | Endpoint          | 說明                        |
| ------ | ----------------- | -------------------------- |
| GET    | `/source`         | 從外部來源取得匯率資料         |
| POST   | `/transform`      | 將來源資料轉換為 ExchangeRate |
| POST   | `/create`         | 建立新的匯率資料              |
| GET    | `/read?id={uuid}` | 讀取指定匯率資料              |
| PUT    | `/update/{id}`    | 更新指定匯率資料              |
| DELETE | `/delete/{id}`    | 刪除指定匯率資料              |

---

## 📥 API 詳細說明

### 🔹 1. 取得匯率來源資料

**GET** `/source`

* 無需參數
* 回傳資料來源格式

### 🔹 2. 轉換來源匯率資料為 ExchangeRate 物件

**POST** `/transform`

* **Request Body**: [`ExchangeRateSourceResponse`](#exchangeratesourceresponse)

* **Response**: `List<ExchangeRate>`

### 🔹 3. 建立匯率資料

**POST** `/create`

* **Request Body**: `ExchangeRateCreateRequest`

```json
{
    "currencyCode": "GBP",
    "currencyNameZh": "英鎊",
    "currencySymbol": "&pound;",
    "rate": 43984.0203,
    "description": "British Pound Sterling",
    "rateTime": "2024-09-02T15:07:20"
}
```

* **Response**: [`ExchangeRate`](#exchangerate)

---

### 🔹 4. 讀取指定匯率資料

**GET** `/read?id={uuid}`

* 以 query string 傳入 `id`
* **Response**: `ExchangeRate`

---

### 🔹 5. 更新匯率資料

**PUT** `/update/{id}`

* **Path Variable**: `id`（UUID）
* **Request Body**: `ExchangeRateUpdateRequest`

```json
{
  "rate": 155.01,
  "rateTime": "2024-09-02T18:07:20"
}
```

* **Response**: `ExchangeRate`

---

### 🔹 6. 刪除匯率資料

**DELETE** `/delete/{id}`

* **Path Variable**: `id`（UUID）
* **Response**: 空的成功回應

---

## 📘 Data Model

### ExchangeRateSourceResponse

```json
{
    "time": {
        "updated": "Sep 2, 2024 07:07:20 UTC",
        "updatedISO": "2024-09-02T07:07:20+00:00",
        "updateduk": "Sep 2, 2024 at 08:07 BST"
    },
    "disclaimer": "just for test",
    "chartName": "Bitcoin",
    "bpi": {
        "USD": {
            "code": "USD",
            "symbol": "&#36;",
            "rate": "57,756.298",
            "description": "United States Dollar",
            "rate_float": 57756.2984
        },
        "GBP": {
            "code": "GBP",
            "symbol": "&pound;",
            "rate": "43,984.02",
            "description": "British Pound Sterling",
            "rate_float": 43984.0203
        },
        "EUR": {
            "code": "EUR",
            "symbol": "&euro;",
            "rate": "52,243.287",
            "description": "Euro",
            "rate_float": 52243.2865
        }
    }
}
```

### ExchangeRate

```json
{
  "id": "uuid",
  "currencyCode": "JPY",
  "currencyNameZh": "日圓",
  "currencySymbol": "¥",
  "rate": 154.25,
  "description": "Japanese Yen",
  "rateTime": "2024-09-02T15:07:20",
  "createdAt": "2025-06-09T14:12:14",
  "updatedAt": "2025-06-09T14:12:14"
}
```

---

## 🧪 測試建議

* 建議使用 Postman 或 curl 測試 API 路由與 CRUD 功能
* JSON 序列化請確認支援 `LocalDateTime`（註冊 `jackson-datatype-jsr310`）

---
