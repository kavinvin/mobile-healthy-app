package co.kavinvin.mobilehealthyapp.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import co.kavinvin.mobilehealthyapp.R
import co.kavinvin.mobilehealthyapp.utils.setFragment
import co.kavinvin.mobilehealthyapp.utils.toast
import co.kavinvin.mobilehealthyapp.utils.toaster
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface PostService {
    @GET("posts")
    fun postList(): Call<List<Post>>
}

class PostFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        post_back_button.setOnClickListener { _ ->
            setFragment(MenuFragment())
        }

        val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(PostService::class.java)
        val posts = service.postList()
        posts.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>?, response: Response<List<Post>>) {
                val postsList = response.body() ?: emptyList()
                post_list.adapter = PostAdapter(context!!, postsList)
                post_list.setOnItemClickListener { adapterView, view, i, l ->
                    setFragment(CommentFragment().apply { postId = postsList[i].id })
                }
            }
            override fun onFailure(call: Call<List<Post>>?, t: Throwable?) {
                toaster().maybeUnknownErrorToast(t?.message).show()
            }
        })
    }

}

data class Post(val userId: Int, val id: Int, val title: String, val body: String)

class PostAdapter(private val ctx: Context, private val data: List<Post>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.fragment_post_item, p2, false)
        rowView.apply {
            val post = data[p0]
            post_title.text = "${post.id} : ${post.title}"
            post_body.text = post.body
        }
        return rowView
    }

    override fun getItem(p0: Int): Any = data[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int = data.size

}