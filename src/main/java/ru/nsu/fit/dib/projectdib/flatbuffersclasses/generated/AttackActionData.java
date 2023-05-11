// automatically generated by the FlatBuffers compiler, do not modify

package ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class AttackActionData extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_2_0_8(); }
  public static AttackActionData getRootAsAttackActionData(ByteBuffer _bb) { return getRootAsAttackActionData(_bb, new AttackActionData()); }
  public static AttackActionData getRootAsAttackActionData(ByteBuffer _bb, AttackActionData obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public AttackActionData __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public int attackingId() { int o = __offset(4); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int attackedId() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int attack() { int o = __offset(8); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int damage() { int o = __offset(10); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public boolean isKilled() { int o = __offset(12); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }

  public static int createAttackActionData(FlatBufferBuilder builder,
      int attackingId,
      int attackedId,
      int attack,
      int damage,
      boolean isKilled) {
    builder.startTable(5);
    AttackActionData.addDamage(builder, damage);
    AttackActionData.addAttack(builder, attack);
    AttackActionData.addAttackedId(builder, attackedId);
    AttackActionData.addAttackingId(builder, attackingId);
    AttackActionData.addIsKilled(builder, isKilled);
    return AttackActionData.endAttackActionData(builder);
  }

  public static void startAttackActionData(FlatBufferBuilder builder) { builder.startTable(5); }
  public static void addAttackingId(FlatBufferBuilder builder, int attackingId) { builder.addInt(0, attackingId, 0); }
  public static void addAttackedId(FlatBufferBuilder builder, int attackedId) { builder.addInt(1, attackedId, 0); }
  public static void addAttack(FlatBufferBuilder builder, int attack) { builder.addInt(2, attack, 0); }
  public static void addDamage(FlatBufferBuilder builder, int damage) { builder.addInt(3, damage, 0); }
  public static void addIsKilled(FlatBufferBuilder builder, boolean isKilled) { builder.addBoolean(4, isKilled, false); }
  public static int endAttackActionData(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }
  public static void finishAttackActionDataBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
  public static void finishSizePrefixedAttackActionDataBuffer(FlatBufferBuilder builder, int offset) { builder.finishSizePrefixed(offset); }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public AttackActionData get(int j) { return get(new AttackActionData(), j); }
    public AttackActionData get(AttackActionData obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

