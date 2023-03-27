package org.tech.town.bookreview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import org.tech.town.bookreview.data.AppDatabase
import org.tech.town.bookreview.data.model.Book
import org.tech.town.bookreview.data.model.Review
import org.tech.town.bookreview.databinding.ActivityDetailBinding
import java.util.Optional.empty

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "BookSearchDB"
        ).build()

        val model = intent.getParcelableExtra<Book>("bookModel")
        binding.titleTextView.text = model?.title.orEmpty()
        binding.descriptionTextView.text = model?.description.orEmpty()
        Glide.with(binding.coverImageView.context)
            .load(model?.coverSmallUrl.orEmpty())
            .into(binding.coverImageView)



        Thread {
            val review = db.reviewDao().getOneReview(model?.id.orEmpty())
//
//            runOnUiThread {
//                binding.reviewEditText.setText(review?.review.orEmpty())
//            }

        }.start()

        binding.saveButton.setOnClickListener {
            Thread {
                db.reviewDao().saveReview(
                    Review(
                        model?.id.orEmpty(),
                        binding.reviewEditText.text.toString()
                    )
                )
            }.start()
        }
    }
}