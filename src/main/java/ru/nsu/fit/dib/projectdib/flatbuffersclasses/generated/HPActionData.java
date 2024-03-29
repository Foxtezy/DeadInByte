// automatically generated by the FlatBuffers compiler, do not modify

package ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class HPActionData extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_2_0_8(); }
  public static HPActionData getRootAsHPActionData(ByteBuffer _bb) { return getRootAsHPActionData(_bb, new HPActionData()); }
  public static HPActionData getRootAsHPActionData(ByteBuffer _bb, HPActionData obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public HPActionData __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public int attackingId() { int o = __offset(4); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int attackedId() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int attackedHp() { int o = __offset(8); return o != 0 ? bb.getInt(o + bb_pos) : 0; }

  public static int createHPActionData(FlatBufferBuilder builder,
      int attackingId,
      int attackedId,
      int attackedHp) {
    builder.startTable(3);
    HPActionData.addAttackedHp(builder, attackedHp);
    HPActionData.addAttackedId(builder, attackedId);
    HPActionData.addAttackingId(builder, attackingId);
    return HPActionData.endHPActionData(builder);
  }

  public static void startHPActionData(FlatBufferBuilder builder) { builder.startTable(3); }
  public static void addAttackingId(FlatBufferBuilder builder, int attackingId) { builder.addInt(0, attackingId, 0); }
  public static void addAttackedId(FlatBufferBuilder builder, int attackedId) { builder.addInt(1, attackedId, 0); }
  public static void addAttackedHp(FlatBufferBuilder builder, int attackedHp) { builder.addInt(2, attackedHp, 0); }
  public static int endHPActionData(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }
  public static void finishHPActionDataBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
  public static void finishSizePrefixedHPActionDataBuffer(FlatBufferBuilder builder, int offset) { builder.finishSizePrefixed(offset); }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public HPActionData get(int j) { return get(new HPActionData(), j); }
    public HPActionData get(HPActionData obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

