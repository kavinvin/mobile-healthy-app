package co.kavinvin.mobilehealthyapp.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import co.kavinvin.mobilehealthyapp.R
import co.kavinvin.mobilehealthyapp.utils.setFragment
import co.kavinvin.mobilehealthyapp.utils.toaster
import kotlinx.android.synthetic.main.fragment_comment.*
import kotlinx.android.synthetic.main.fragment_comment_item.view.*
import kotlinx.android.synthetic.main.fragment_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class CommentFragment() : Fragment() {

    var postId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        comment_back_button.setOnClickListener { _ ->
            setFragment(PostFragment())
        }

        val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(CommentService::class.java)
        val comments = service.commentList(postId)
        comments.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>?, response: Response<List<Comment>>) {
                val commentList = response.body() ?: emptyList()
                comment_list.adapter = CommentAdapter(context!!, commentList)
                comment_list.setOnItemClickListener { adapterView, view, i, l ->

                }
            }
            override fun onFailure(call: Call<List<Comment>>?, t: Throwable?) {
                toaster().maybeUnknownErrorToast(t?.message).show()
            }
        })
    }

}

interface CommentService {
    @GET("posts/{id}/comments")
    fun commentList(@Path("id") id: Int): Call<List<Comment>>
}

data class Comment(val postId: Int,
                   val id: Int,
                   val name: String,
                   val email: String,
                   val body: String)

class CommentAdapter(private val ctx: Context, private val data: List<Comment>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.fragment_comment_item, p2, false)
        rowView.apply {
            val comment = data[p0]
            comment_title.text = "${comment.postId} : ${comment.id}"
            comment_body.text = comment.body
        }
        return rowView
    }

    override fun getItem(p0: Int): Any = data[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int = data.size

}