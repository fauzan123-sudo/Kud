package com.example.kud.data.adapter

//class AdapterCheck(val context: Context) : RecyclerView.Adapter<AdapterCheck.ViewHolder>() {
//
//    inner class ViewHolder(val binding: ItemCheckoutBinding) : RecyclerView.ViewHolder(binding.root)
//
//    var listener: DeleteItemCheckOut? = null
//
//    private val differCallback = object : DiffUtil.ItemCallback<CheckOut>() {
//        override fun areItemsTheSame(oldItem: CheckOut, newItem: CheckOut): Boolean {
//            return oldItem == newItem
//        }
//
//        override fun areContentsTheSame(oldItem: CheckOut, newItem: CheckOut): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    val differ = AsyncListDiffer(this, differCallback)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
//       ItemCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//    )
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val dataPosition = differ.currentList[position]
//        with(holder) {
//            with(binding) {
//                with(dataPosition) {
//                    totalItems.text = amountItem.toString()
//                    drugName.text = nameItem
//                    val totalPrice = NumberFormat.getNumberInstance(Locale.US)
//                        .format(priceItem)
//                        .replace(",", ".")
//                    drugPrice.text = "Rp. $totalPrice"
//                    drugType.text = category.toString()
//                    imageProduct.load(IMAGE_OBAT + imageItem)
//
//                    val totil = priceItem!! * amountItem!!
//                    textView9.text = totil.toString()
//
//
//                    grantTotal()
//                    grandTotalItem()
//
//                    var counter =0
//                    btnPlus.setOnClickListener {
//                        var amountItem = totalItems.text.toString().toInt()
//                        counter++
//                        amountItem += counter
//                        Toast.makeText(context, "nambah $amountItem ", Toast.LENGTH_SHORT).show()
////                        btnPlus.isEnabled = amountItem <= stockItem!!
////                        btnMinus.isEnabled = amountItem >= 1
////                        val total = amountItem + 1
////                        totalItems.setText(total.toString())
////                        listener?.addItemCheckOut(holder, absoluteAdapterPosition)
//                    }
//                    totalItems.addTextChangedListener(object : TextWatcher {
//                        override fun beforeTextChanged(
//                            p0: CharSequence?, p1: Int, p2: Int, p3: Int
//                        ) {
//
//                        }
//
//                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
////                            grantTotal()
//
//                        }
//
//                        override fun afterTextChanged(qty: Editable?) {
//                            val totils = priceItem * qty.toString().toInt()
//
//                            textView9.text = "$totils"
//
//                            var totalItem = 0
//                            for (i in 0 until differ.currentList.size) {
//                                totalItem += holder.binding.textView9.text.toString().toInt()
//
//                            }
////                            Toast.makeText(context, "$totalItem", Toast.LENGTH_SHORT).show()
//                            listener?.grandTotalItem(totalItem)
////                            grandTotalItem()
//
//
//                            var total = 0
//                            var totalItems = 0
//                            var totalData = 0
//
//                            for (i in 0 until differ.currentList.size) {
//                                total += differ.currentList[i].priceItem!!
//                                totalItems += differ.currentList[i].amountItem!!
//                                totalData = total * totalItems
//                            }
//                            listener?.grandTotal(totalData)
//                        }
//
//                    })
//
//                    btnMinus.setOnClickListener {
//                        if (totalItems.text.toString().toInt() != 1) {
//                            val amountItem = totalItems.text.toString().toInt()
//                            btnMinus.isEnabled = amountItem > 0
//                            val total = amountItem - 1
//                            totalItems.text = total.toString()
////                            listener?.addItemCheckOut(holder, absoluteAdapterPosition)
//                        }
//                    }
//                }
//
//                binding.deleteItem.setOnClickListener {
////                    if (listener != null) {
////                        listener?.deleteItemCheckOut(holder, absoluteAdapterPosition)
//////                        Toast.makeText(context, "not null", Toast.LENGTH_SHORT).show()
////                    } else {
//////                        Toast.makeText(context, "listener is null", Toast.LENGTH_SHORT).show()
////                    }
//
//
//                }
//            }
//        }
//    }
//
//    private fun grandTotalItem() {
//        var totalItem = 0
//        for (i in 0 until differ.currentList.size) {
//            totalItem += differ.currentList[i].amountItem!!
//        }
//        listener?.grandTotalItem(totalItem)
//    }
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }
//
//    class OnClickListeners(val clickListener: (checkOut: CheckOut) -> Unit) {
//        fun onClick(checkOut: CheckOut) = clickListener(checkOut)
//    }
//
//    private fun grantTotal() {
//        var total = 0
//        var totalItem = 0
//        var totalData = 0
//        for (i in 0 until differ.currentList.size) {
//            total += differ.currentList[i].priceItem!!
//            totalItem += differ.currentList[i].amountItem!!
//            totalData = total * totalItem
//        }
//        listener?.grandTotal(total)
//    }
//
//    interface OnAdapterListener{
//        fun onUpdateTotal()
//    }
//
//}

