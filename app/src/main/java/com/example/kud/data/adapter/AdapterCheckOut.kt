package com.example.kud.data.adapter

//class AdapterCheckOut(val context: Context) : RecyclerView.Adapter<AdapterCheckOut.ViewHolder>() {
//    private lateinit var binding: ItemCheckoutBinding
//    var listener: DeleteItemCheckOut? = null
//
//    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
//        fun setData(item: CheckOut) {
//            var totalItem = item.amountItem
//            val myPosition = differ.currentList[absoluteAdapterPosition]
//            binding.apply {
//                with(item) {
////                    textView9.text = Helper().gantiRupiah(amountItem * priceItem!!)
//                    totalItems.text = amountItem.toString()
//                    drugName.text = nameItem
//                    drugPrice.text = Helper().gantiRupiah("$priceItem")
//                    drugType.text = category
//                    imageProduct.load(Constants.IMAGE_OBAT + item.imageItem)
//                }
//
//                deleteItem.setOnClickListener {
//                    listener?.deleteItemCheckOut(layoutPosition, item)
//                }
//
//                btnPlus.setOnClickListener {
//                    totalItem++
//                    item.amountItem = totalItem
//                    totalItems.text = totalItem.toString()
//                    textView9.text = Helper().gantiRupiah(item.priceItem!! * totalItem)
//                    listener?.upsert(myPosition)
//
//                }
//
//                btnMinus.setOnClickListener {
//                    totalItem--
//                    if (totalItem <= 1) return@setOnClickListener
//                    item.amountItem = totalItem
//                    totalItems.text = totalItem.toString()
//                    textView9.text = Helper().gantiRupiah(item.priceItem!! * totalItem)
//                    listener?.upsert(myPosition)
//                }
//
//                totalItems.addTextChangedListener(object : TextWatcher {
//                    override fun beforeTextChanged(
//                        p0: CharSequence?, p1: Int, p2: Int, p3: Int
//                    ) {
//
//                    }
//
//                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                        listener?.upsert(myPosition)
//                    }
//
//                    override fun afterTextChanged(p0: Editable?) {
//                        listener?.upsert(myPosition)
//                    }
//
//                })
//
//
//            }
//        }
//    }
//
//    private val differCallback = object : DiffUtil.ItemCallback<CheckOut>() {
//        override fun areItemsTheSame(oldItem: CheckOut, newItem: CheckOut): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: CheckOut, newItem: CheckOut): Boolean {
//            return oldItem.id == newItem.id
//        }
//    }
//
//    val differ = AsyncListDiffer(this, differCallback)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        binding = ItemCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder()
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.setData(differ.currentList[position])
//        holder.setIsRecyclable(false)
//
//    }
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }
//
//}