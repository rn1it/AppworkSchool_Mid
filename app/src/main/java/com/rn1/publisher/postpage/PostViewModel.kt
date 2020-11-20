package com.rn1.publisher.postpage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.rn1.publisher.model.Article
import com.rn1.publisher.model.Author
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class PostViewModel: ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    val title = MutableLiveData<String>()
    val category = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    private val _postArticleSuccess = MutableLiveData<Boolean>()

    val postArticleSuccess : LiveData<Boolean>
        get() = _postArticleSuccess


    private val _showToast = MutableLiveData<Boolean>()

    val showToast : LiveData<Boolean>
        get() = _showToast

    /**
     * post article
     */
    fun post(){

        val tile = title.value
        val content = content.value
        val category = category.value

        val author = Author("wayne@school.appworks.tw","waynechen323", "AKA小安老師")


        if (tile.isNullOrBlank() || content.isNullOrBlank() || category.isNullOrBlank()){
            _showToast.value = true
        } else if (author == null) { //TODO check author but now is always true
            _showToast.value = true
        } else {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val authorString = moshi.adapter(Author::class.java).toJson(author)

            Log.d("aaa","aa = $authorString")

            val article = Article(authorString, tile, content, category)

            val colRefArticle = db.collection("articles")

            colRefArticle.add(article).addOnSuccessListener {
                it.update("id", it.id)
                _postArticleSuccess.value = true
            }
        }
    }

    fun onNavigateDone(){
        _postArticleSuccess.value = null
    }

    fun onShowToastDone(){
        _showToast.value = null
    }
}