/**
 * @brief It defines the inventory interface
 *
 * @file inventory.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 1.0
 * @date 26-10-2019
 * @copyright GNU Public License
 */
#ifndef INVENTORY_H
#define INVENTORY_H

#include "types.h"

/**
  @brief It defines set structure
 */
typedef struct _Inventory Inventory;

/**
 * @author Junco de las Heras Valenzuela
 * @brief Reserve memory for a inventory
 *
 * @date 1-11-2019
 * @param int the max_size of the inventory
 * @return NULL if it can't reserve the memory otherwise a Inventory*
 */
Inventory* inventory_create(int max_size);
/**
 * @author Junco de las Heras Valenzuela
 * @brief Deallocate memory for inventory
 *
 * @date 1-11-2019
 * @param Inventory* the set to deallocate memory
 * @return OK if it could deallocate correctly, otherwise ERROR
 */
STATUS inventory_destroy(Inventory* inventory);
/**
 * @author Junco de las Heras Valenzuela
 * @brief get the max size of the inventory
 *
 * @date 1-11-2019
 * @param Inventory* the inventory to get the max size
 * @return ERROR if there was an error, otherwise the max size of the inventory
 */
int inventory_get_max_size(Inventory* inventory);
/**
 * @author Junco de las Heras Valenzuela
 * @brief set the size of the inventory
 *
 * @date 1-11-2019
 * @param Inventory* the inventory to get the max size
 * @param int the new size of the max size of the inventory
 * @return ERROR if there was an error, otherwise the max size of the inventory
 */
STATUS inventory_set_max_size(Inventory* inventory, int new_size);
/**
 * @author Junco de las Heras Valenzuela
 * @brief get the size of the test
 *
 * @date 1-11-2019
 * @param Inventory* the inventory to get the size
 * @return ERROR if there was an error, otherwise the size of the inventory
 */
int inventory_get_size(Inventory* inventory);
/**
 * @author Junco de las Heras Valenzuela
 * @brief get an array of id of the inserted elements
 *
 * @date 1-11-2019
 * @param Inventory* the set to ids
 * @return NULL if there was an error, otherwise an array with the ids
 */
Id* inventory_get_ids(Inventory* inventory);

/**
 * @brief Searches a given Id in an Inventory
 *
 * inventory_has_id Looks for the given Id in the inventory's list of elements
 *
 * @author Fabian Gutierrez
 * @date 03-11-2019
 *
 * @param inventory the Inventory to be evaluated
 * @param id the Id to be searched
 * @return TRUE if the Id is in the inventory, FALSE otherwise
 */
BOOL inventory_has_id(Inventory *inventory, Id id);
/**
 * @author Junco de las Heras Valenzuela
 * @brief insert element
 *
 * @date 1-11-2019
 * @param Inventory* the inventory to insert the element
 * @param Id the id to be inserted
 * @return ERROR if there was an error, otherwise OK
 */
STATUS inventory_insert(Inventory* inventory, Id id);
/**
 * @author Junco de las Heras Valenzuela
 * @brief delete element with id object
 *
 * @date 26-10-2019
 * @param Inventory* the set to delete the element
 * @param Id the object to be deleted
 * @return ERROR if there was an error, otherwise OK
 */
STATUS inventory_delete(Inventory* inventory, Id id);
/**
 * @author Junco de las Heras Valenzuela
 * @brief print the ids of the elements inserted in the inventory
 *
 * @date 26-10-2019
 * @param Inventory* the inventory to be printed
 * @return ERROR if there was an error, otherwise OK
 */
STATUS inventory_print(Inventory* inventory);

#endif
