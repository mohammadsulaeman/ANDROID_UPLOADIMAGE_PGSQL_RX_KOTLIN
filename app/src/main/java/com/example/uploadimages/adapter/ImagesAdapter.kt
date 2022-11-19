package com.example.uploadimages.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uploadimages.R
import com.example.uploadimages.constants.Constant
import com.example.uploadimages.network.api.PicassoTrustAll
import com.example.uploadimages.network.model.Images
import com.example.uploadimages.ui.UpdateDeleteActivity
import de.hdodenhof.circleimageview.CircleImageView

class ImagesAdapter(var context: Context, var datalist : List<Images>) : RecyclerView.Adapter<ImagesAdapter.ItemRowHolder>()
{



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item,parent,false)
        return ItemRowHolder(view)
    }

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        val images = datalist.get(position)
        holder.title.text = images.title
        if (!images.image.isEmpty()){
            PicassoTrustAll.getInstance(context)!!
                .load(Constant.IMAGESCOVER + images.image)
                .into(holder.image);
        }
        holder.lineKlik.setOnClickListener {
            val updatedelete = Intent(context, UpdateDeleteActivity::class.java)
            updatedelete.putExtra("image_id",images.idimage)
            updatedelete.putExtra("cover",images.image)
            updatedelete.putExtra("title",images.title)
            context.startActivity(updatedelete)
        }

    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    inner class ItemRowHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image = itemView.findViewById<CircleImageView>(R.id.image_view_read)
        val title : TextView = itemView.findViewById(R.id.text_view_nama_read)
        val lineKlik : LinearLayout = itemView.findViewById(R.id.line_klik_item)
    }
}