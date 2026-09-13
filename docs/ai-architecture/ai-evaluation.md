# AI evaluation

| # | Test case | Measurable acceptance criterion |
|---|---|---|
|1|Exact name|Top result has matching ID|
|2|Case variation|Same IDs returned|
|3|Category search|All results match category|
|4|Description search|Matching product returned|
|5|Multi-term ranking|Two-term match ranks first|
|6|Stop words|No error or invented claim|
|7|No match|Empty recommendations and sources|
|8|No-match answer|States no in-stock catalog product|
|9|Inactive item|Never returned|
|10|Zero stock|Never returned|
|11|Limit|At most three assistant results|
|12|Prices|100% database-value fidelity|
|13|Sources|100% retrieved-ID fidelity|
|14|Empty question|HTTP 400|
|15|Overlong question|HTTP 400 over 500 chars|
|16|Anonymous assistant|HTTP 200|
|17|Anonymous enrichment|HTTP 401|
|18|Non-admin enrichment|HTTP 403|
|19|Title normalization|Title-cased output|
|20|Missing description|Reviewable fallback only|
|21|No category|`Uncategorized` result|
|22|No persistence|Zero product rows changed|
|23|Demo config|Starts without API key|
|24|OpenAI missing key|Clear startup failure|

All cases must pass; inactive/out-of-stock leakage and source/price mismatches have a zero-tolerance threshold.

## LangGraph Workflow Cases

| # | Scenario | Expected evidence |
|---|---|---|
|25|Catalog request|Router selects `catalog`; Catalog Agent invokes `search_products`; response contains returned product fields only.| 
|26|Order-status request without token|Router selects `order`; final response asks the user to sign in; no order data is returned.| 
|27|Order-status request with token|Order Support Agent invokes `get_my_orders`; returned records belong to the token subject as enforced by Spring Boot.| 
|28|Order-changing request|Graph returns an interrupt and status `awaiting_approval`; no backend mutation is called.| 
|29|Rejected approval|Resume with `rejected`; final response confirms that no action was taken.| 
|30|Revision feedback|Resume with `revise` and feedback; final response records feedback and confirms no action was taken.| 

For each workflow test, record the session ID, route, tools invoked, source IDs, interrupt payload where applicable, and final response. Never record bearer tokens.
