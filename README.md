# Malabe Tuk-Tuk & Three-Wheeler Spares Depot
> GitHub: https://github.com/SeharaChami/CM1601_JAVA
A JavaFX desktop application for managing spare parts inventory at a tuk-tuk spares depot.
Built as part of the CM1601 Programming Fundamentals coursework at IIT / RGU.


## What it does

- Add, update, and delete spare parts from the inventory
- View all inventory items grouped by category and sorted by part code
- Search inventory using multiple filters at once (name, brand, category, price range, quantity)
- Monitor low-stock items on the home screen — each item has its own stock threshold (default: 10)
- Randomly select 4 dealers from a legacy dealer file and display them sorted by location
- Point-of-sale cart — add items, specify quantities, and process transactions
  - 5% bulk discount applies when buying 3 or more of the same item
  - 10% synergy discount applies when the cart has both an ENGINE and an ELECTRICAL part
- All changes are saved to a plain text file
- Every add, delete, and sale is recorded in an audit log (`audit_log.txt`)

## Project structure

src/
  main/
    java/tuktukjava/
      Item.java                  — data model for a spare part
      Inventory.java             — holds all items, handles search, sort, low-stock
      Cart.java                  — POS cart logic and discount calculation
      CartItem.java              — single item in the cart
      Dealer.java                — data model for a dealer
      RandomDealers.java         — random selection and alphabetical sort of dealers
      DirtyDataCleaner.java      — abstract base class for parsing legacy files
      InventoryCleaner.java      — cleans and normalises the inventory legacy file
      DealerCleaner.java         — cleans the dealer legacy file
      FileManager.java           — reads and writes all text files
      controllers/
        HomeController.java      — home screen with low-stock panel
        AddItemController.java   — add a new part
        UpdateController.java    — search and update an existing part
        DeleteController.java    — delete a part
        InventoryController.java — view all inventory
        SearchController.java    — multi-criteria search
        CartController.java      — POS cart screen
        DealerController.java    — dealer viewer
  resources/
    inventory_clean.txt          — main data file (pipe-separated, 9 fields per line)
    inventory_legacy.txt         — original dirty data file (loaded once if clean file missing)
    dealers_legacy.txt           — dealer data file
    audit_log.txt                — append-only log of all changes
    images/                      — item images

test/
  java/tuktukjava/
    ItemValidationTest.java
    CartTest.java
    InventoryTest.java
    DealerTest.java
    DirtyDataParsingTest.java
    AuditLogTest.java


## Data file format

Each line in `inventory_clean.txt` has 9 pipe-separated fields:

P001|Bajaj 4-Stroke Piston|Bajaj|Rs 4500.00|13|ENGINE|2023-10-12|piston4s.jpg|10


|  Field    | Index | Example               |
|-----------|-------|-----------------------|
|   Code    |   0   | P001                  |
|   Name    |   1   | Bajaj 4-Stroke Piston |
|   Brand   |   2   | Bajaj                 |
|   Price   |   3   | Rs 4500.00            |
| Quantity  |   4   | 13                    |
| Category  |   5   | ENGINE                |
|   Date    |   6   | 2023-10-12            |
|   Image   |   7   | piston4s.jpg          |
| Threshold |   8   | 10                    |

If the legacy file is loaded instead (no clean file present), the dirty data parser handles inconsistent delimiters, varied price formats, and multiple date formats automatically.

## Requirements

- Java 17
- JavaFX 17.0.6
- Maven

---

## How to run

```bash
mvn javafx:run
```

Or open the project in IntelliJ IDEA and run `malabeTukTukApplication.java`.

---

## How to run tests

```bash
mvn test
```

Test classes are in `src/test/java/tuktukjava/`. They cover cart discount logic, low-stock detection, dirty data parsing, dealer selection, search filtering, and audit logging.

---

## Notes

- The threshold field (index 8) was added later. Old 8-field data lines are automatically migrated to 9 fields with a default threshold of 10 when loaded.
- Item images should be placed in `src/main/resources/images/`.
- The audit log is append-only — it is never overwritten, only added to.
