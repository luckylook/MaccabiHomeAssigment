package com.example.maccabi.data.repositories

import android.util.Log
import com.example.maccabi.data.domain.Category
import com.example.maccabi.data.domain.Product
import com.example.maccabi.data.entity.CategoryEntity
import com.example.maccabi.data.entity.ProductEntity
import com.example.maccabi.data.local.ICategoryDAO
import com.example.maccabi.data.local.IProductDAO
import com.example.maccabi.data.remote.CatalogService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatalogRepository @Inject constructor(
    dispatcher: CoroutineDispatcher,
    private val remote: CatalogService,
    private val categoryDAO: ICategoryDAO,
    private val productDAO: IProductDAO
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    val categoriesFlow: Flow<List<Category>> = flow {
        emitAll(categoryDAO.getAllCategories().flatMapConcat { categories ->
            if (categories!!.isEmpty()) {
                getFromRemote()
                categoryDAO.getAllCategories().map { newCategories ->
                    getCategoriesWithProducts(newCategories)
                }
            } else {
                flowOf(getCategoriesWithProducts(categories))
            }
        })
    }.flowOn(dispatcher)

    private suspend fun getCategoriesWithProducts(categories: List<CategoryEntity>?): List<Category> {
        if (categories == null) {
            throw Exception("Something went wrong!")
        }
        return categories.map { categoryEntity ->
            val allProducts = productDAO.getAllProductsByCategoryName(categoryEntity.name)
            val products = allProducts.map { product ->
                Product(name = product.name, image = product.image, price = product.price, quantity = product.quantity)
            }
            val totalProductsInStock = products.sumOf { it.quantity }
            Category(categoryEntity.name, categoryEntity.thumbnail, allProducts.size, totalProductsInStock, products)
        }
    }


    private suspend fun getFromRemote() {
        try {
            val catalogContract = remote.getCatalog()
            val categoryMap = mutableMapOf<String, CategoryEntity>()
            val productList = mutableListOf<ProductEntity>()

            catalogContract.productContracts.forEach { productContract ->
                val categoryName = productContract.category
                if (!categoryMap.containsKey(categoryName)) {
                    categoryMap[categoryName] = CategoryEntity(
                        name = categoryName,
                        thumbnail = productContract.thumbnail,
                        timestamp = System.currentTimeMillis()
                    )
                }

                val productEntity = ProductEntity(
                    category = productContract.category,
                    name = productContract.title,
                    image = productContract.images.firstOrNull() ?: "",
                    price = productContract.price,
                    quantity = productContract.stock,
                    timestamp = System.currentTimeMillis()
                )
                productList.add(productEntity)
            }

            saveInDB(categoryMap.values.toList(), productList)
        } catch (error: Exception) {
            throw Exception("Something went wrong in the server please try again later.")
        }

    }

    private suspend fun saveInDB(categories: List<CategoryEntity>, productList: List<ProductEntity>) {
        categoryDAO.insertAll(categories)
        productDAO.insertAll(productList)
    }
}