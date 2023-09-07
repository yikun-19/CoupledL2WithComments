/***************************************************************************************
* Copyright (c) 2020-2021 Institute of Computing Technology, Chinese Academy of Sciences
* Copyright (c) 2020-2021 Peng Cheng Laboratory
*
* XiangShan is licensed under Mulan PSL v2.
* You can use this software according to the terms and conditions of the Mulan PSL v2.
* You may obtain a copy of Mulan PSL v2 at:
*          http://license.coscl.org.cn/MulanPSL2
*
* THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
* MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
*
* See the Mulan PSL v2 for more details.
***************************************************************************************/

package utility

import chisel3._
import chisel3.util._
import freechips.rocketchip.util._

// indicates where the memory access request comes from
// a dupliacte of this is in Xiangshan.package and HuanCun.common
object MemReqSource_internal extends Enumeration {
  val NoWhere = Value("NoWhere")

  val CPUInst = Value("CPUInst")
  val CPULoadData = Value("CPULoadData")
  val CPUStoreData = Value("CPUStoreData")
  val CPUAtomicData = Value("CPUAtomicData")
  val L1InstPrefetch = Value("L1InstPrefetch")
  val L1DataPrefetch = Value("L1DataPrefetch")
  val PTW = Value("PTW")
  val L2Prefetch = Value("L2Prefetch")
  val ReqSourceCount = Value("ReqSourceCount")

  val reqSourceBits = log2Ceil(ReqSourceCount.id)
}

// Used to indicate the source of the req (L1I/L1D/PTW)
case object ReqSourceKey extends ControlKey[UInt]("reqSource")

case class ReqSourceField() extends BundleField(ReqSourceKey) {
  override def data: UInt = Output(UInt(MemReqSource_internal.reqSourceBits.W))

  override def default(x: UInt): Unit = {
    x := MemReqSource_internal.NoWhere.id.U(MemReqSource_internal.reqSourceBits.W)
  }
}
